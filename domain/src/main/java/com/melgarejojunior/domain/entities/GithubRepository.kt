package com.melgarejojunior.domain.entities

data class GithubRepository(
    val id: Long,
    val nodeId: String,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val description: String,
    val fork: Boolean,
    val url: String,
    val stargazersCount: Long,
    val forksCount: Long,
)

