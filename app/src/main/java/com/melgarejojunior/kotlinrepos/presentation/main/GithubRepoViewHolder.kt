package com.melgarejojunior.kotlinrepos.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
            authorImage.loadImage(item.owner.avatarUrl)
        }
    }

    private fun ImageView.loadImage(avatarUrl: String?) {
        Glide
            .with(context)
            .load(avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .circleCrop()
            .into(this)
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
