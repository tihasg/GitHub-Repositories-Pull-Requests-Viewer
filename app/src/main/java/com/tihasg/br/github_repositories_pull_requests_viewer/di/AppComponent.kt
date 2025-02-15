package com.tihasg.br.github_repositories_pull_requests_viewer.di

import com.tihasg.br.github_repositories_pull_requests_viewer.MyApp
import com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests.PullRequestsActivity
import com.tihasg.br.github_repositories_pull_requests_viewer.presentation.repositories.RepositoriesActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(app: MyApp)
    fun inject(activity: RepositoriesActivity)
    fun inject(activity: PullRequestsActivity)
}
