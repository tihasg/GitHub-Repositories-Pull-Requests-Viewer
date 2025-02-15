package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests

import androidx.lifecycle.ViewModel
import com.tihasg.br.domain.usecases.GetPullRequestsUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PullRequestsViewModel @Inject constructor(
    private val getPullRequestsUseCase: GetPullRequestsUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val intentsSubject: PublishSubject<PullRequestsIntent> = PublishSubject.create()
    private val statesObservable: Observable<PullRequestsViewState> = compose()

    fun processIntents(intent: PullRequestsIntent) {
        intentsSubject.onNext(intent)
    }

    fun states(): Observable<PullRequestsViewState> =
        statesObservable.observeOn(AndroidSchedulers.mainThread())

    private fun compose(): Observable<PullRequestsViewState> {
        return intentsSubject.switchMap { intent ->
            when (intent) {
                is PullRequestsIntent.LoadPullRequests -> loadPullRequests(
                    intent.owner,
                    intent.repo
                )
            }
        }
    }

    private fun loadPullRequests(owner: String, repo: String): Observable<PullRequestsViewState> {
        return getPullRequestsUseCase.execute(owner, repo)
            .subscribeOn(Schedulers.io())
            .toObservable()
            .map { prs ->
                PullRequestsViewState(isLoading = false, pullRequests = prs)
            }
            .startWith(PullRequestsViewState(isLoading = true))
            .onErrorReturn { error ->
                PullRequestsViewState(isLoading = false, error = error)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
