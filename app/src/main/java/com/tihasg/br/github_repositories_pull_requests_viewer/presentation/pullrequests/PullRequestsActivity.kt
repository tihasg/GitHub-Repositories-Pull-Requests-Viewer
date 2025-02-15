package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.tihasg.br.core.base.BaseActivity
import com.tihasg.br.core.utils.updateData
import com.tihasg.br.core.utils.visibleOrGone
import com.tihasg.br.github_repositories_pull_requests_viewer.MyApp
import com.tihasg.br.github_repositories_pull_requests_viewer.databinding.ActivityPullRequestsBinding
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class PullRequestsActivity : BaseActivity<ActivityPullRequestsBinding>() {

    @Inject
    lateinit var viewModel: PullRequestsViewModel

    private lateinit var adapter: PullRequestsAdapter

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityPullRequestsBinding {
        return ActivityPullRequestsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        (application as MyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setupRecyclerView()

        val owner = intent.getStringExtra("owner") ?: ""
        val repo = intent.getStringExtra("repo") ?: ""

        subscribeToViewModel()
        viewModel.processIntents(PullRequestsIntent.LoadPullRequests(owner, repo))
    }

    private fun setupRecyclerView() {
        adapter = PullRequestsAdapter { pullRequest ->
            val url = "https://github.com/${pullRequest.user?.login}/${pullRequest.title}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeToViewModel() {
        viewModel.states()
            .subscribeBy { state ->
                binding.progressBar.visibleOrGone(state.isLoading)
                if (!state.isLoading) {
                    if (state.error != null) {
                        AlertDialog.Builder(this)
                            .setTitle("Erro")
                            .setMessage(state.error.message ?: "Ocorreu um erro inesperado")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    } else {
                        adapter.updateData(state.pullRequests)
                    }
                }
            }
            .also { disposable -> addDisposable(disposable) }
    }
}
