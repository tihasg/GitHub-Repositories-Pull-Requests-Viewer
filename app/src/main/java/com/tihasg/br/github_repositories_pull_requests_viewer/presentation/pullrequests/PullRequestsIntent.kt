package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests

sealed class PullRequestsIntent {
    data class LoadPullRequests(val owner: String, val repo: String) : PullRequestsIntent()
}
