package com.melgarejojunior.data.remote.datasource

import com.melgarejojunior.data.remote.entities.RepositoryListResponse
import com.melgarejojunior.data.remote.service.GithubRepositoriesService

class GithubRepositoriesRemoteDataSourceImpl(
    private val service: GithubRepositoriesService,
) : GithubRepositoriesRemoteDataSource {
    override suspend fun getKotlinRepositories(page: Int): RepositoryListResponse {
        return makeRequest { service.getKotlinRepositories(page) }
    }
}