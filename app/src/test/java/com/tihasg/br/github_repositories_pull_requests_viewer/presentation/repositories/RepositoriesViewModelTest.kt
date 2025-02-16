package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.repositories

import com.tihasg.br.domain.model.ListRepository
import com.tihasg.br.domain.model.Owner
import com.tihasg.br.domain.model.Repository
import com.tihasg.br.domain.usecases.GetRepositoriesUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class RepositoriesViewModelTest {

    private lateinit var viewModel: RepositoriesViewModel
    private val getRepositoriesUseCase: GetRepositoriesUseCase = mockk()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = RepositoriesViewModel(getRepositoriesUseCase)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `load repositories success`() {
        val dummyItems = listOf(
            Repository(
                id = 1,
                name = "Repo1",
                description = "Desc1",
                owner = Owner("user1", "url1"),
                stargazers_count = 100,
                forks_count = 10
            )
        )
        val dummyResponse = ListRepository(items = dummyItems)
        every { getRepositoriesUseCase.execute("Kotlin", 1) } returns Single.just(dummyResponse)

        val testObserver = viewModel.states().test()
        viewModel.processIntents(RepositoriesIntent.LoadRepositories)

        testObserver.assertValueAt(0) { it.isLoading }
        testObserver.assertValueAt(1) { !it.isLoading && it.repositories == dummyItems }
    }

    @Test
    fun `load repositories error`() {
        val error = Exception("Network error")
        every { getRepositoriesUseCase.execute("Kotlin", 1) } returns Single.error(error)

        val testObserver = viewModel.states().test()
        viewModel.processIntents(RepositoriesIntent.LoadRepositories)

        testObserver.assertValueAt(0) { it.isLoading }
        testObserver.assertValueAt(1) { !it.isLoading && it.error == error }
    }

    @Test
    fun `load repositories with empty list`() {
        val dummyResponse = ListRepository(items = emptyList())
        every { getRepositoriesUseCase.execute("Kotlin", 1) } returns Single.just(dummyResponse)

        val testObserver = viewModel.states().test()
        viewModel.processIntents(RepositoriesIntent.LoadRepositories)

        testObserver.assertValueAt(0) { it.isLoading }
        testObserver.assertValueAt(1) { !it.isLoading && it.repositories.isEmpty() }
    }

    @Test
    fun `load next page success`() {
        val dummyItems = listOf(
            Repository(
                id = 2,
                name = "Repo2",
                description = "Desc2",
                owner = Owner("user2", "url2"),
                stargazers_count = 200,
                forks_count = 20
            )
        )
        val dummyResponse = ListRepository(items = dummyItems)
        every { getRepositoriesUseCase.execute("Kotlin", 2) } returns Single.just(dummyResponse)

        val testObserver = viewModel.states().test()
        viewModel.processIntents(RepositoriesIntent.LoadNextPage("Kotlin", 2))

        testObserver.assertValueAt(0) { it.isLoading }
        testObserver.assertValueAt(1) { !it.isLoading && it.repositories == dummyItems }
    }

    @Test
    fun `multiple intents processed correctly`() {
        val dummyItems = listOf(
            Repository(
                id = 3,
                name = "Repo3",
                description = "Desc3",
                owner = Owner("user3", "url3"),
                stargazers_count = 300,
                forks_count = 30
            )
        )
        val dummyResponse = ListRepository(items = dummyItems)
        every { getRepositoriesUseCase.execute("Kotlin", 1) } returns Single.just(dummyResponse)

        val testObserver = viewModel.states().test()
        viewModel.processIntents(RepositoriesIntent.LoadRepositories)
        viewModel.processIntents(RepositoriesIntent.LoadRepositories)

        testObserver.assertValueAt(0) { it.isLoading }
        testObserver.assertValueAt(1) { !it.isLoading && it.repositories == dummyItems }
    }
}
