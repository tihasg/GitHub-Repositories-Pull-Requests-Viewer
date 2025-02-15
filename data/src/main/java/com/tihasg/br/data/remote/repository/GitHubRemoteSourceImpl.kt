package com.tihasg.br.data.remote.repository

import com.tihasg.br.data.mappers.toDomain
import com.tihasg.br.data.remote.api.GitHubApi
import com.tihasg.br.data.remote.response.PullRequestResponse
import com.tihasg.br.domain.datasource.GitHubRemoteSource
import com.tihasg.br.domain.model.ListRepository
import com.tihasg.br.domain.model.PullRequest
import io.reactivex.Single
import javax.inject.Inject

class GitHubRemoteSourceImpl @Inject constructor(
    private val api: GitHubApi
) : GitHubRemoteSource {
    override fun getRepositories(language: String, page: Int): Single<ListRepository> {
        val query = "language:$language"
        return api.searchRepositories(query = query, page = page).map { it.toDomain() }
    }

    override fun getPullRequests(owner: String, repo: String): Single<List<PullRequest>> {
        return api.getPullRequests(owner, repo).map { it.map(PullRequestResponse::toDomain) }
    }
}
