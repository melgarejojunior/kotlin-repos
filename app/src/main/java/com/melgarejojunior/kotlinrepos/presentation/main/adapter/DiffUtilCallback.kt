package com.melgarejojunior.kotlinrepos.presentation.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.melgarejojunior.domain.entities.GithubRepository

class DiffUtilCallback : DiffUtil.ItemCallback<GithubRepository>() {
    override fun areItemsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean {
        return oldItem == newItem
    }
}