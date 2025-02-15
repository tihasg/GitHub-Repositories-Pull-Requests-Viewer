package com.tihasg.br.data.mappers

import com.tihasg.br.data.remote.response.ListRepositoryResponse
import com.tihasg.br.data.remote.response.OwnerResponse
import com.tihasg.br.data.remote.response.PullRequestResponse
import com.tihasg.br.data.remote.response.RepositoryResponse
import com.tihasg.br.data.remote.response.UserResponse
import com.tihasg.br.domain.model.PullRequest
import com.tihasg.br.domain.model.User

fun PullRequestResponse.toDomain(): PullRequest {
    return PullRequest(
        id = id ?: 0,
        title = title ?: "",
        user = user?.toDomain(),
        created_at = created_at ?: "",
        body = body ?: ""
    )
}

fun UserResponse.toDomain(): User {
    return User(
        login = login,
        avatar_url = avatar_url
    )
}


fun ListRepositoryResponse.toDomain(): com.tihasg.br.domain.model.ListRepository {
    return com.tihasg.br.domain.model.ListRepository(
        items = items.map { it.toDomain() }
    )
}

fun RepositoryResponse.toDomain(): com.tihasg.br.domain.model.Repository {
    return com.tihasg.br.domain.model.Repository(
        id = id,
        name = name,
        description = description ?: "",
        owner = owner.toDomain(),
        stargazers_count = stargazers_count,
        forks_count = forks_count,
    )
}

fun OwnerResponse.toDomain(): com.tihasg.br.domain.model.Owner {
    return com.tihasg.br.domain.model.Owner(
        login = login,
        avatar_url = avatar_url
    )
}