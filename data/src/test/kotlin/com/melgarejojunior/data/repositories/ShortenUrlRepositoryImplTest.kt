package com.melgarejojunior.data.repositories

import app.cash.turbine.test
import com.melgarejojunior.data.remote.datasource.ShortenUrlRemoteDataSource
import com.melgarejojunior.data.remote.entities.ShortenedUrlResponse
import com.melgarejojunior.data.remote.exceptions.HttpException
import com.melgarejojunior.data.remote.exceptions.NullBodyException
import com.melgarejojunior.domain.entities.OriginalUrl
import com.melgarejojunior.domain.entities.ShortenedUrl
import com.melgarejojunior.domain.exceptions.GenericErrorException
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class ShortenUrlRepositoryImplTest {
    private val mockRemoteDataSource = mockk<ShortenUrlRemoteDataSource>(relaxed = true)
    private val repository = ShortenUrlRepositoryImpl(remoteDataSource = mockRemoteDataSource)

    @Test
    fun `shortenUrl Should return a ShortenedUrl when service returns correctly`() = runBlocking {
        // Given
        val expectedShortenedUrl =
            ShortenedUrl(alias = ALIAS, short = SHORT, self = URL)
        every { mockRemoteDataSource.shortenUrl(OriginalUrl(URL)) }.returns(flowOf(
            ShortenedUrlResponse(ALIAS,
                ShortenedUrlResponse.LinksResponse(self = URL, short = SHORT)))
        )

        // When
        val result = repository.shortenUrl(OriginalUrl(URL))

        // Then
        result.test {
            assertEquals(expectedShortenedUrl, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `shortenUrl Should return a GenericErrorException when service returns HttpException`() =
        runBlocking {
            // Given
            every { mockRemoteDataSource.shortenUrl(OriginalUrl(URL)) }.returns(
                flow {
                    throw HttpException(code = 400)
                }
            )

            // When
            val result = repository.shortenUrl(OriginalUrl(URL))

            // Then
            result.test {
                assertEquals(GenericErrorException::class, awaitError()::class)
            }
        }

    @Test
    fun `shortenUrl Should return a GenericErrorException when service returns NullBodyException`() =
        runBlocking {
            // Given
            every { mockRemoteDataSource.shortenUrl(OriginalUrl(URL)) }.returns(
                flow {
                    throw NullBodyException()
                }
            )

            // When
            val result = repository.shortenUrl(OriginalUrl(URL))

            // Then
            result.test {
                assertEquals(GenericErrorException::class, awaitError()::class)
            }
        }

    @Test
    fun `shortenUrl Should return the exception when service returns any other exception`() =
        runBlocking {
            // Given
            every { mockRemoteDataSource.shortenUrl(OriginalUrl(URL)) }.returns(
                flow {
                    throw IllegalStateException()
                }
            )

            // When
            val result = repository.shortenUrl(OriginalUrl(URL))

            // Then
            result.test {
                assertEquals(IllegalStateException::class, awaitError()::class)
            }
        }

    companion object {
        private const val URL = "https://google.com"
        private const val ALIAS = "1234"
        private const val SHORT = "https://short-url/1234"
    }
}