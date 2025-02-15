package com.tihasg.br.data.remote.response

data class PullRequestResponse(
    val id: Long?,
    val title: String?,
    val user: UserResponse?,
    val created_at: String?,
    val body: String?
)
