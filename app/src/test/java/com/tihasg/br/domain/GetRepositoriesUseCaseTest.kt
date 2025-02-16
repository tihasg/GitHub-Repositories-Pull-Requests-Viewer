package com.tihasg.br.domain

import com.tihasg.br.domain.datasource.GitHubRemoteSource
import com.tihasg.br.domain.model.ListRepository
import com.tihasg.br.domain.model.Owner
import com.tihasg.br.domain.model.Repository
import com.tihasg.br.domain.usecases.GetRepositoriesUseCase
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

class GetRepositoriesUseCaseTest {

    private lateinit var useCase: GetRepositoriesUseCase
    private val repository: GitHubRemoteSource = mockk()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        useCase = GetRepositoriesUseCase(repository)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `execute returns repository list on success`() {
        val dummyItems = listOf(
            Repository(
                id = 1,
                name = "Test unit lab magalu",
                description = "Desc1",
                owner = Owner("user1", "url1"),
                stargazers_count = 100,
                forks_count = 10
            )
        )
        val dummyResponse = ListRepository(items = dummyItems)

        every { repository.getRepositories("Kotlin", 1) } returns Single.just(dummyResponse)

        val testObserver = useCase.execute("Kotlin", 1).test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue { it.items == dummyItems }

        verify(exactly = 1) { repository.getRepositories("Kotlin", 1) }
    }

    @Test
    fun `execute returns error when repository fetch fails`() {
        val error = Exception("Network error")
        every { repository.getRepositories("Kotlin", 1) } returns Single.error(error)

        val testObserver = useCase.execute("Kotlin", 1).test()

        testObserver.assertNotComplete()
        testObserver.assertError(error)

        verify(exactly = 1) { repository.getRepositories("Kotlin", 1) }
    }
}
