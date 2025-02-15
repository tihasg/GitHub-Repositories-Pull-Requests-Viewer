package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tihasg.br.domain.model.PullRequest
import com.tihasg.br.github_repositories_pull_requests_viewer.databinding.ItemPullRequestBinding

class PullRequestsAdapter(
    private val onClick: (PullRequest) -> Unit
) : ListAdapter<PullRequest, PullRequestsAdapter.PullRequestViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val binding =
            ItemPullRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PullRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PullRequestViewHolder(private val binding: ItemPullRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pr: PullRequest) {
            binding.prTitle.text = pr.title
            binding.prDate.text = pr.created_at
            binding.prBody.text = pr.body
            Glide.with(binding.root.context)
                .load(pr.user?.avatar_url)
                .into(binding.prUserImage)
            binding.root.setOnClickListener { onClick(pr) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean =
                oldItem == newItem
        }
    }
}
