package com.melgarejojunior.data.remote.datasource

import com.melgarejojunior.data.remote.entities.RepositoryListResponse

internal interface GithubRepositoriesRemoteDataSource {
    suspend fun getKotlinRepositories(page: Int): RepositoryListResponse
}