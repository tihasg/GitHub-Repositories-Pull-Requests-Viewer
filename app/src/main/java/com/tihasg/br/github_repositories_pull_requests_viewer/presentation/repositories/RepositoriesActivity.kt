package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.repositories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.tihasg.br.core.base.BaseActivity
import com.tihasg.br.core.utils.Constants.EXTRA_OWNER
import com.tihasg.br.core.utils.Constants.EXTRA_REPO
import com.tihasg.br.core.utils.updateData
import com.tihasg.br.core.utils.visibleOrGone
import com.tihasg.br.domain.model.Repository
import com.tihasg.br.github_repositories_pull_requests_viewer.MyApp
import com.tihasg.br.github_repositories_pull_requests_viewer.R
import com.tihasg.br.github_repositories_pull_requests_viewer.databinding.ActivityRepositoriesBinding
import com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests.PullRequestsActivity
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RepositoriesActivity : BaseActivity<ActivityRepositoriesBinding>() {

    @Inject
    lateinit var viewModel: RepositoriesViewModel
    private lateinit var adapter: RepositoriesAdapter

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityRepositoriesBinding {
        return ActivityRepositoriesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        subscribeToViewModel()
        viewModel.processIntents(RepositoriesIntent.LoadRepositories)
    }

    private fun setupRecyclerView() {
        adapter = RepositoriesAdapter { repository: Repository ->
            val intent = Intent(this, PullRequestsActivity::class.java).apply {
                putExtra(EXTRA_OWNER, repository.owner?.login)
                putExtra(EXTRA_REPO, repository.name)
            }
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
                            .setTitle(getString(R.string.error_title))
                            .setMessage(state.error.message ?: getString(R.string.unexpected_error))
                            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    } else {
                        adapter.updateData(state.repositories)
                    }
                }
            }
            .also { disposable -> addDisposable(disposable) }
    }
}
