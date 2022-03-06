package com.melgarejojunior.data.remote.mapper

import com.melgarejojunior.data.remote.entities.OwnerResponse
import com.melgarejojunior.data.remote.entities.RepositoryResponse
import com.melgarejojunior.domain.entities.GithubRepository
import com.melgarejojunior.domain.entities.Owner

class RepositoryResponseToRepository {

    fun map(source: RepositoryResponse): GithubRepository {
        return GithubRepository(
            id = source.id ?: 0L,
            nodeId = source.nodeId.orEmpty(),
            name = source.name.orEmpty(),
            fullName = source.fullName.orEmpty(),
            owner = source.owner.toOwner(),
            description = source.description.orEmpty(),
            fork = source.fork ?: false,
            url = source.url.orEmpty(),
            stargazersCount = source.stargazersCount ?: 0,
            forksCount = source.forksCount ?: 0,
        )
    }

    private fun OwnerResponse?.toOwner(): Owner {
        return this?.run { Owner(login.orEmpty(), id, nodeId, avatarUrl) } ?: Owner.empty()
    }
}