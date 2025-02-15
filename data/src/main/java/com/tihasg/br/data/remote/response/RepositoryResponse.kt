package com.tihasg.br.data.remote.response


data class ListRepositoryResponse(
    val items: List<RepositoryResponse>
)

data class RepositoryResponse(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: OwnerResponse,
    val stargazers_count: Int,
    val forks_count: Int
)
