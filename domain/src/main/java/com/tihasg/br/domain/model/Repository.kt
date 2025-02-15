package com.tihasg.br.domain.model

data class Repository(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: Owner?,
    val stargazers_count: Int,
    val forks_count: Int
)
