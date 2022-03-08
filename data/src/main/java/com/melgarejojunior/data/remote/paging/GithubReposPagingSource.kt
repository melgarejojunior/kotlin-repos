package com.melgarejojunior.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.melgarejojunior.data.remote.datasource.GithubRepositoriesRemoteDataSource
import com.melgarejojunior.data.remote.exceptions.HttpException
import com.melgarejojunior.data.remote.mapper.RepositoryResponseToRepository
import com.melgarejojunior.domain.entities.GithubRepository
import java.io.IOException

internal class GithubReposPagingSource(
    private val remoteDataSource: GithubRepositoriesRemoteDataSource,
    private val mapper: RepositoryResponseToRepository,
) : PagingSource<Int, GithubRepository>() {

    override val keyReuseSupported: Boolean get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {
        return try {
            val result = remoteDataSource.getKotlinRepositories(
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
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}