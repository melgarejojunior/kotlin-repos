package com.melgarejojunior.data.remote.paging

import androidx.paging.PagingSource
import com.melgarejojunior.data.remote.datasource.GithubRepositoriesRemoteDataSource
import com.melgarejojunior.data.remote.entities.RepositoryListResponse
import com.melgarejojunior.data.remote.exceptions.HttpException
import com.melgarejojunior.data.remote.mapper.RepositoryResponseToRepository
import com.melgarejojunior.data.remote.githubRepositoryStub
import com.melgarejojunior.data.remote.repositoryResponseStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException
import java.net.HttpURLConnection
import kotlin.test.assertEquals

class GithubReposPagingSourceTest {

    private val remoteDataSource = mockk<GithubRepositoriesRemoteDataSource>(relaxed = true)
    private val mapper = RepositoryResponseToRepository()
    private val pagingSource = GithubReposPagingSource(remoteDataSource, mapper)

    @Test
    fun `load with page 1 Should return a Page with data`() = runBlocking {
        // Given
        val givenPage = 1
        coEvery { remoteDataSource.getKotlinRepositories(givenPage) }.coAnswers {
            RepositoryListResponse(
                totalCount = 1,
                incompleteResults = true,
                items = listOf(repositoryResponseStub())
            )
        } coAndThen {
            RepositoryListResponse(
                totalCount = 0,
                incompleteResults = false,
                items = listOf()
            )
        }
        val expected = PagingSource.LoadResult.Page(
            data = listOf(githubRepositoryStub()),
            prevKey = null,
            nextKey = givenPage.plus(1)
        )

        // When
        val result = pagingSource.load(PagingSource.LoadParams.Refresh(
            key = null,
            loadSize = 30,
            placeholdersEnabled = true
        ))

        // Then
        assertEquals(result, expected)
    }

    @Test
    fun `load with page 1 Should return a IOException`() = runBlocking {
        // Given
        val givenPage = 1
        val expectedException = IOException(Throwable())
        coEvery { remoteDataSource.getKotlinRepositories(givenPage) }.coAnswers {
            throw expectedException
        }

        // When
        val result = pagingSource.load(PagingSource.LoadParams.Refresh(
            key = null,
            loadSize = 30,
            placeholdersEnabled = true
        ))

        // Then
        assertEquals(result, PagingSource.LoadResult.Error(expectedException))
    }

    @Test
    fun `load with page 1 Should return a HttpException`() = runBlocking {
        // Given
        val givenPage = 1
        val expectedException = HttpException(HttpURLConnection.HTTP_BAD_REQUEST)
        coEvery { remoteDataSource.getKotlinRepositories(givenPage) }.coAnswers {
            throw expectedException
        }

        // When
        val result = pagingSource.load(PagingSource.LoadParams.Refresh(
            key = null,
            loadSize = 30,
            placeholdersEnabled = true
        ))

        // Then
        assertEquals(result, PagingSource.LoadResult.Error(expectedException))
    }
}