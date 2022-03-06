package com.melgarejojunior.kotlinrepos.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.melgarejojunior.domain.entities.GithubRepository

class DiffUtilCallBack : DiffUtil.ItemCallback<GithubRepository>() {
    override fun areItemsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean {
        return oldItem == newItem
    }
}