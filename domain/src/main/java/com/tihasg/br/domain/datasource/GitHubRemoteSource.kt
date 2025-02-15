package com.tihasg.br.domain.datasource

import com.tihasg.br.domain.model.ListRepository
import com.tihasg.br.domain.model.PullRequest
import io.reactivex.Single

interface GitHubRemoteSource {
    fun getRepositories(language: String, page: Int): Single<ListRepository>
    fun getPullRequests(owner: String, repo: String): Single<List<PullRequest>>
}