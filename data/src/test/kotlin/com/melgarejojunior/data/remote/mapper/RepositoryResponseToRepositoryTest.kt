package com.melgarejojunior.data.remote.mapper

import com.melgarejojunior.data.remote.entities.RepositoryResponse
import com.melgarejojunior.data.remote.githubRepositoryStub
import com.melgarejojunior.data.remote.repositoryResponseStub
import com.melgarejojunior.domain.entities.GithubRepository
import com.melgarejojunior.domain.entities.Owner
import org.junit.Test
import kotlin.test.assertEquals

class RepositoryResponseToRepositoryTest {
    private val mapper = RepositoryResponseToRepository()

    @Test
    fun `map Should filter info and pass data correctly`() {
        // Given
        val givenRepositoryResponse = repositoryResponseStub()
        val expectedGithubRepository = githubRepositoryStub()

        // When
        val result = mapper.map(givenRepositoryResponse)

        // Then
        assertEquals(expectedGithubRepository, result)
    }

    @Test
    fun `map Should eliminate null info on domain entity`() {
        // Given
        val givenRepositoryResponse = RepositoryResponse()
        val expectedGithubRepository = GithubRepository(
            id = 0L,
            nodeId = "",
            name = "",
            fullName = "",
            owner = Owner.empty(),
            description = "",
            fork = false,
            url = "",
            stargazersCount = 0L,
            forksCount = 0L,
        )

        // When
        val result = mapper.map(givenRepositoryResponse)

        // Then
        assertEquals(expectedGithubRepository, result)
    }
}