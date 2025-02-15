package com.tihasg.br.domain.usecases

import com.tihasg.br.domain.datasource.GitHubRemoteSource
import com.tihasg.br.domain.model.PullRequest
import io.reactivex.Single
import javax.inject.Inject

class GetPullRequestsUseCase @Inject constructor(
    private val repository: GitHubRemoteSource
) {
    fun execute(owner: String, repo: String): Single<List<PullRequest>> {
        return repository.getPullRequests(owner, repo)
    }
}
