package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests

import com.tihasg.br.domain.model.PullRequest
import com.tihasg.br.domain.model.User
import com.tihasg.br.domain.usecases.GetPullRequestsUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class PullRequestsViewModelTest {

    private lateinit var viewModel: PullRequestsViewModel
    private val getPullRequestsUseCase: GetPullRequestsUseCase = mockk()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = PullRequestsViewModel(getPullRequestsUseCase)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `load pull requests success`() {

        val dummyPullRequests = listOf(
            PullRequest(
                id = 1L,
                title = "PR Title",
                user = User("user1", "url1"),
                created_at = "2025-02-15",
                body = "PR Body"
            )
        )
        every { getPullRequestsUseCase.execute("owner", "repo") } returns Single.just(
            dummyPullRequests
        )

        val testObserver = viewModel.states().test()
        viewModel.processIntents(PullRequestsIntent.LoadPullRequests("owner", "repo"))

        testObserver.assertValueAt(0) { it.isLoading }
        testObserver.assertValueAt(1) { !it.isLoading && it.pullRequests == dummyPullRequests }
    }

    @Test
    fun `load pull requests error`() {
        val error = Exception("Error fetching pull requests")
        every { getPullRequestsUseCase.execute("owner", "repo") } returns Single.error(error)

        val testObserver = viewModel.states().test()
        viewModel.processIntents(PullRequestsIntent.LoadPullRequests("owner", "repo"))

        testObserver.assertValueAt(0) { it.isLoading }
        testObserver.assertValueAt(1) { !it.isLoading && it.error == error }
    }
}
