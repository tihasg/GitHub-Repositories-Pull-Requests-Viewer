package com.tihasg.br.data.remote.api

import com.tihasg.br.data.remote.response.ListRepositoryResponse
import com.tihasg.br.data.remote.response.PullRequestResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int
    ): Single<ListRepositoryResponse>

    @GET("repos/{owner}/{repo}/pulls")
    fun getPullRequests(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<PullRequestResponse>>
}
