package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests

import com.tihasg.br.domain.model.PullRequest

data class PullRequestsViewState(
    val isLoading: Boolean = false,
    val pullRequests: List<PullRequest> = emptyList(),
    val error: Throwable? = null
)
