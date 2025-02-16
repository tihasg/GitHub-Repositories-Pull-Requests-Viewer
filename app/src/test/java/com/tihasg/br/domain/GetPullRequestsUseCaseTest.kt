package com.tihasg.br.domain

import com.tihasg.br.domain.datasource.GitHubRemoteSource
import com.tihasg.br.domain.model.PullRequest
import com.tihasg.br.domain.model.User
import com.tihasg.br.domain.usecases.GetPullRequestsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetPullRequestsUseCaseTest {

    private lateinit var useCase: GetPullRequestsUseCase
    private val repository: GitHubRemoteSource = mockk()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        useCase = GetPullRequestsUseCase(repository)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `execute returns pull request list on success`() {
        val dummyPullRequests = listOf(
            PullRequest(
                id = 1L,
                title = "Test unit lab magalu",
                user = User("user1", "avatar_url1"),
                created_at = "2025-02-16",
                body = "This PR fixes a bug in feature X"
            ),
            PullRequest(
                id = 2L,
                title = "Improve performance",
                user = User("user2", "avatar_url2"),
                created_at = "2025-02-17",
                body = "Refactored code for better performance"
            )
        )

        every { repository.getPullRequests("owner", "repo") } returns Single.just(dummyPullRequests)

        val testObserver = useCase.execute("owner", "repo").test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue { it == dummyPullRequests }

        verify(exactly = 1) { repository.getPullRequests("owner", "repo") }
    }

    @Test
    fun `execute returns error when repository fetch fails`() {
        val error = Exception("API error")
        every { repository.getPullRequests("owner", "repo") } returns Single.error(error)

        val testObserver = useCase.execute("owner", "repo").test()

        testObserver.assertNotComplete()
        testObserver.assertError(error)

        verify(exactly = 1) { repository.getPullRequests("owner", "repo") }
    }
}
