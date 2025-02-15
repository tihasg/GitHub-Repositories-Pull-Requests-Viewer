package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.repositories

import com.tihasg.br.domain.model.Repository


data class RepositoriesViewState(
    val isLoading: Boolean = false,
    val repositories: List<Repository> = emptyList(),
    val error: Throwable? = null
)
