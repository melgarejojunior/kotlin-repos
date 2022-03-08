package com.melgarejojunior.data.remote.datasource

import com.google.gson.Gson
import com.melgarejojunior.data.remote.entities.RepositoryListResponse
import com.melgarejojunior.data.remote.exceptions.HttpException
import com.melgarejojunior.data.remote.exceptions.NullBodyException
import com.melgarejojunior.data.remote.repositoryResponseStub
import com.melgarejojunior.data.remote.service.GithubRepositoriesService
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

class GithubRepositoriesRemoteDataSourceImplTest {
    private val mockWebServer = MockWebServer()
    private lateinit var dataSource: GithubRepositoriesRemoteDataSource

    @Before
    fun setup() {
        mockWebServer.start()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: GithubRepositoriesService =
            retrofit.create(GithubRepositoriesService::class.java)
        dataSource = GithubRepositoriesRemoteDataSourceImpl(service)
    }

    @Test
    fun `getKotlinRepositories Should return a RepositoryListResponse when service responds successfully`() =
        runBlocking {
            // Given
            val expectedResponse = RepositoryListResponse(
                totalCount = 1,
                incompleteResults = true,
                items = listOf(repositoryResponseStub())
            )
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_CREATED)
                    .setBody(Gson().toJson(expectedResponse))
            )
            // When
            val result = dataSource.getKotlinRepositories(1)

            // Then
            assertEquals(result, expectedResponse)
        }

    @Test(expected = NullBodyException::class)
    fun `getKotlinRepositories Should return NullBodyException when service responds with an empty body`() =
        runBlocking {
            // Given
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_CREATED)
                    .setBody("")
            )

            // When
            // Then
            val result = dataSource.getKotlinRepositories(1)
        }

    @Test(expected = HttpException::class)
    fun `getKotlinRepositories Should return HttpException when service responds with a fail`() =
        runBlocking {
            // Given

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            )

            // When
            val result = dataSource.getKotlinRepositories(1)
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}