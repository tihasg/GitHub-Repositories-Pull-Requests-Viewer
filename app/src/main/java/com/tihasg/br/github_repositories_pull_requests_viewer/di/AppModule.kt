package com.tihasg.br.github_repositories_pull_requests_viewer.di

import android.content.Context
import com.tihasg.br.data.remote.api.GitHubApi
import com.tihasg.br.data.remote.repository.GitHubRemoteSourceImpl
import com.tihasg.br.domain.datasource.GitHubRemoteSource
import com.tihasg.br.domain.usecases.GetPullRequestsUseCase
import com.tihasg.br.domain.usecases.GetRepositoriesUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideGitHubRemoteSource(api: GitHubApi): GitHubRemoteSource =
        GitHubRemoteSourceImpl(api)

    @Provides
    fun provideGetRepositoriesUseCase(remoteSource: GitHubRemoteSource): GetRepositoriesUseCase =
        GetRepositoriesUseCase(remoteSource)

    @Provides
    fun provideGetPullRequestsUseCase(remoteSource: GitHubRemoteSource): GetPullRequestsUseCase =
        GetPullRequestsUseCase(remoteSource)
}
