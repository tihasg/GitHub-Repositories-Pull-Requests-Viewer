package com.tihasg.br.github_repositories_pull_requests_viewer

import android.app.Application
import com.tihasg.br.github_repositories_pull_requests_viewer.di.AppComponent
import com.tihasg.br.github_repositories_pull_requests_viewer.di.AppModule
import com.tihasg.br.github_repositories_pull_requests_viewer.di.DaggerAppComponent

class MyApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
