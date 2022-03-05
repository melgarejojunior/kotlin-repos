package com.melgarejojunior.data.remote.service

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepositoriesService {

    @GET("/search/repositories")
    fun getRepositories(
        @Query("language") language: String,
        @Query("sort") sortType: String,
        @Query("page") page: Int,
    )
}