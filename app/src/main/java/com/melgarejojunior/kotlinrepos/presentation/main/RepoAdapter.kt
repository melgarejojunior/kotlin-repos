package com.melgarejojunior.kotlinrepos.presentation.main

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.melgarejojunior.domain.entities.GithubRepository

class RepoAdapter :
    PagingDataAdapter<GithubRepository, GithubRepoViewHolder>(DiffUtilCallBack()) {
    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder {
        return GithubRepoViewHolder.inflate(parent)
    }
}

