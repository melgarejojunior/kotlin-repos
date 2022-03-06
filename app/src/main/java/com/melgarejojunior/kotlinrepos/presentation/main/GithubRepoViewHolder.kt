package com.melgarejojunior.kotlinrepos.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melgarejojunior.domain.entities.GithubRepository
import com.melgarejojunior.kotlinrepos.databinding.ItemBinding

class GithubRepoViewHolder private constructor(
    private val binding: ItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GithubRepository) {
        with(binding) {
            name.text = item.name
            starCount.text = item.stargazersCount.toString()
            forksCount.text = item.forksCount.toString()
            authorName.text = item.owner.login
        }
    }

    companion object {
        fun inflate(parent: ViewGroup): GithubRepoViewHolder {
            return GithubRepoViewHolder(
                ItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}