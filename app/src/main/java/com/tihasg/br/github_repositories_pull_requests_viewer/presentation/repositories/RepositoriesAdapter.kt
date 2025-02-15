package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tihasg.br.domain.model.Repository
import com.tihasg.br.github_repositories_pull_requests_viewer.databinding.ItemRepositoryBinding

class RepositoriesAdapter(
    private val onClick: (Repository) -> Unit
) : ListAdapter<Repository, RepositoriesAdapter.RepositoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            binding.repoName.text = repository.name
            binding.repoDescription.text = repository.description
            binding.repoStars.text = repository.stargazers_count.toString()
            binding.repoForks.text = repository.forks_count.toString()
            Glide.with(binding.root.context)
                .load(repository.owner?.avatar_url)
                .into(binding.repoOwnerImage)
            binding.root.setOnClickListener { onClick(repository) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem == newItem
        }
    }
}
