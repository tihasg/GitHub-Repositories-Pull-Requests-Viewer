package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.repositories

import androidx.lifecycle.ViewModel
import com.tihasg.br.domain.usecases.GetRepositoriesUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RepositoriesViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetRepositoriesUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val intentsSubject: PublishSubject<RepositoriesIntent> = PublishSubject.create()
    private val statesObservable: Observable<RepositoriesViewState> = compose()

    fun processIntents(intent: RepositoriesIntent) {
        intentsSubject.onNext(intent)
    }

    fun states(): Observable<RepositoriesViewState> =
        statesObservable.observeOn(AndroidSchedulers.mainThread())

    private fun compose(): Observable<RepositoriesViewState> {
        return intentsSubject.switchMap { intent ->
            when (intent) {
                is RepositoriesIntent.LoadRepositories -> loadRepositories("Kotlin", 1)
                is RepositoriesIntent.LoadNextPage -> loadRepositories(intent.language, intent.page)
            }
        }
    }

    private fun loadRepositories(language: String, page: Int): Observable<RepositoriesViewState> {
        return getRepositoriesUseCase.execute(language, page)
            .subscribeOn(Schedulers.io())
            .toObservable()
            .map { response ->
                RepositoriesViewState(isLoading = false, repositories = response.items)
            }
            .startWith(RepositoriesViewState(isLoading = true))
            .onErrorReturn { error ->
                RepositoriesViewState(isLoading = false, error = error)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
