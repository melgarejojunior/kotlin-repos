package com.melgarejojunior.data.remote.datasource

import app.cash.turbine.test
import com.melgarejojunior.data.remote.entities.ShortenedUrlResponse
import com.melgarejojunior.data.remote.exceptions.HttpException
import com.melgarejojunior.data.remote.exceptions.NullBodyException
import com.melgarejojunior.data.remote.service.ShortenUrlService
import com.melgarejojunior.data.repositories.stub.STUB_ALIAS
import com.melgarejojunior.data.repositories.stub.STUB_ORIGINAL_URL
import com.melgarejojunior.data.repositories.stub.STUB_SHORTENED_URL
import com.melgarejojunior.data.repositories.stub.getShortenedUrlSuccessfulResponse
import com.melgarejojunior.domain.entities.OriginalUrl
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import kotlin.test.assertEquals

class ShortenUrlRemoteDataSourceImplTest {

    private val mockWebServer = MockWebServer()
    private lateinit var dataSource: ShortenUrlRemoteDataSource

    @Before
    fun setup() {
        mockWebServer.start()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ShortenUrlService = retrofit.create(ShortenUrlService::class.java)
        dataSource = ShortenUrlRemoteDataSourceImpl(service)
    }

    @Test
    fun `shortenUrl Should return ShortenedUrlResponse when service responds successfully`() =
        runBlocking {
            // Given
            val url = STUB_ORIGINAL_URL
            val expectedResponse = ShortenedUrlResponse(
                STUB_ALIAS,
                ShortenedUrlResponse.LinksResponse(
                    STUB_ORIGINAL_URL, STUB_SHORTENED_URL
                )
            )
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_CREATED)
                    .setBody(getShortenedUrlSuccessfulResponse)
            )

            // When
            val result = dataSource.shortenUrl(OriginalUrl(url))

            // Then
            result.test {
                assertEquals(expectedResponse, awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `shortenUrl Should return NullBodyException when service responds with an empty body`() =
        runBlocking {
            // Given
            val url = STUB_ORIGINAL_URL

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_CREATED)
                    .setBody("")
            )

            // When
            val result = dataSource.shortenUrl(OriginalUrl(url))

            // Then
            result.test {
                assertEquals(NullBodyException::class, awaitError()::class)
            }
        }

    @Test
    fun `shortenUrl Should return HttpException when service responds with a fail`() =
        runBlocking {
            // Given
            val url = STUB_ORIGINAL_URL

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            )

            // When
            val result = dataSource.shortenUrl(OriginalUrl(url))

            // Then
            result.test {
                assertEquals(HttpException::class, awaitError()::class)
            }
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}