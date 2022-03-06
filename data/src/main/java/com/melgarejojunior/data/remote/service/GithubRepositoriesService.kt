package com.melgarejojunior.data.remote.service

import com.melgarejojunior.data.remote.entities.RepositoryListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepositoriesService {

    @GET("/search/repositories?q=language:kotlin&sort=stars")
    suspend fun getKotlinRepositories(
        @Query("page") page: Int,
    ): Response<RepositoryListResponse>
}