package com.tihasg.br.domain.datasource

import com.tihasg.br.domain.model.PullRequest
import com.tihasg.br.domain.model.Repository
import io.reactivex.Single

interface GitHubRemoteSource {
    fun getRepositories(language: String, page: Int): Single<Repository>
    fun getPullRequests(owner: String, repo: String): Single<List<PullRequest>>
}