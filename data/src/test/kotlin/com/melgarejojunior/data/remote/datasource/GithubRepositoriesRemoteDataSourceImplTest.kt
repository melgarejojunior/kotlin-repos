package com.melgarejojunior.data.remote.datasource

import com.melgarejojunior.data.remote.service.GithubRepositoriesService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            // When
            // Then
        }
}