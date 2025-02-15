package com.tihasg.br.domain.usecases

import com.tihasg.br.domain.datasource.GitHubRemoteSource
import com.tihasg.br.domain.model.ListRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(
    private val repository: GitHubRemoteSource
) {
    fun execute(language: String, page: Int): Single<ListRepository> {
        return repository.getRepositories(language, page)
    }
}
