package com.melgarejojunior.data.remote.mapper

import com.melgarejojunior.data.remote.entities.OwnerResponse
import com.melgarejojunior.data.remote.entities.RepositoryResponse
import com.melgarejojunior.domain.entities.GithubRepository
import com.melgarejojunior.domain.entities.Owner

private const val LOGIN = "login user name"
private const val AVATAR_URL = "https://avatar.url.com"
private const val ID = 123L
private const val NODE_ID = "abc"
private const val NAME = "Repo Name"
private const val FULL_NAME = "Full Repo Name"
private const val DESCRIPTION = "Description"
private const val FORK: Boolean = false
private const val URL: String = "https://github.com/repourl"
private const val STARGAZERS_COUNT = 35L
private const val FORKS_COUNT = 13L
private val OWNER: Owner = Owner(LOGIN, ID, NODE_ID, AVATAR_URL)


fun repositoryResponseStub() = RepositoryResponse(
    id = ID,
    nodeId = NODE_ID,
    name = NAME,
    fullName = FULL_NAME,
    owner = OwnerResponse(login = LOGIN, id = ID, nodeId = NODE_ID, avatarUrl = AVATAR_URL),
    description = DESCRIPTION,
    fork = FORK,
    url = URL,
    stargazersCount = STARGAZERS_COUNT,
    forksCount = FORKS_COUNT,
)

fun githubRepositoryStub() = GithubRepository(
    ID,
    NODE_ID,
    NAME,
    FULL_NAME,
    OWNER,
    DESCRIPTION,
    FORK,
    URL,
    STARGAZERS_COUNT,
    FORKS_COUNT,
)