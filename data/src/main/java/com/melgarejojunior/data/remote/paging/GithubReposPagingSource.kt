package com.melgarejojunior.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.melgarejojunior.data.remote.entities.RepositoryResponse
import com.melgarejojunior.data.remote.service.GithubRepositoriesService
import retrofit2.HttpException
import java.io.IOException

class GithubReposPagingSource(
    private val service: GithubRepositoriesService,
) : PagingSource<Int, RepositoryResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryResponse> {
        return try {
            val result = service.getRepositories(
                language = KOTLIN,
                sortType = SORT_BY,
                page = params.key ?: STARTING_PAGE_INDEX)

            LoadResult.Page(
                data = result.items ?: emptyList(),
                prevKey = params.key,
                nextKey = STARTING_PAGE_INDEX.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryResponse>): Int? {
        return STARTING_PAGE_INDEX
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val KOTLIN = "kotlin"
        private const val SORT_BY = "stars"
    }
}