package com.tihasg.br.domain.model

data class PullRequest(
    val id: Long?,
    val title: String?,
    val user: User?,
    val created_at: String?,
    val body: String?
)
