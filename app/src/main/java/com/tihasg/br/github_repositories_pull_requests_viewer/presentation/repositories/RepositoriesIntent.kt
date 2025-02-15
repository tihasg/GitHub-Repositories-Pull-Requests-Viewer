package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.repositories

sealed class RepositoriesIntent {
    object LoadRepositories : RepositoriesIntent()
    data class LoadNextPage(val language: String, val page: Int) : RepositoriesIntent()
}
