package com.tihasg.br.data.remote.repository

import com.tihasg.br.data.remote.api.GitHubApi
import com.tihasg.br.data.remote.response.ListRepositoryResponse
import com.tihasg.br.data.remote.response.OwnerResponse
import com.tihasg.br.data.remote.response.RepositoryResponse
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

class GitHubRemoteSourceImplTest {

    private lateinit var remoteSource: GitHubRemoteSourceImpl
    private val api: GitHubApi = mockk()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        remoteSource = GitHubRemoteSourceImpl(api)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `getRepositories returns repository list on success`() {
        val repositoryResponses = listOf(
            RepositoryResponse(
                id = 1,
                name = "Repo1",
                description = "Description1",
                owner = OwnerResponse("user1", "url1"),
                stargazers_count = 100,
                forks_count = 10
            )
        )
        val listRepositoryResponse = ListRepositoryResponse(items = repositoryResponses)

        every { api.searchRepositories("language:Kotlin", "stars", 1) } returns Single.just(
            listRepositoryResponse
        )

        val testObserver = remoteSource.getRepositories("Kotlin", 1).test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue { it.items.size == repositoryResponses.size }

        verify(exactly = 1) { api.searchRepositories("language:Kotlin", "stars", 1) }
    }

    @Test
    fun `getRepositories returns error when API call fails`() {
        val error = Exception("API error")
        every { api.searchRepositories("language:Kotlin", "stars", 1) } returns Single.error(error)

        val testObserver = remoteSource.getRepositories("Kotlin", 1).test()

        testObserver.assertNotComplete()
        testObserver.assertError(error)

        verify(exactly = 1) { api.searchRepositories("language:Kotlin", "stars", 1) }
    }

    @Test
    fun `getPullRequests returns error when API call fails`() {
        val error = Exception("API error")
        every { api.getPullRequests("owner", "repo") } returns Single.error(error)

        val testObserver = remoteSource.getPullRequests("owner", "repo").test()

        testObserver.assertNotComplete()
        testObserver.assertError(error)

        verify(exactly = 1) { api.getPullRequests("owner", "repo") }
    }
}
