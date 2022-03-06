package com.melgarejojunior.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.melgarejojunior.data.remote.mapper.RepositoryResponseToRepository
import com.melgarejojunior.data.remote.service.GithubRepositoriesService
import com.melgarejojunior.domain.entities.GithubRepository
import retrofit2.HttpException
import java.io.IOException

class GithubReposPagingSource(
    private val service: GithubRepositoriesService,
    private val mapper: RepositoryResponseToRepository,
) : PagingSource<Int, GithubRepository>() {

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {
        return try {
            val result = service.getRepositories(
                page = params.key ?: STARTING_PAGE_INDEX
            )

            LoadResult.Page(
                data = (result.items ?: emptyList()).map(mapper::map),
                prevKey = params.key,
                nextKey = STARTING_PAGE_INDEX.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepository>): Int? {
        return STARTING_PAGE_INDEX
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}