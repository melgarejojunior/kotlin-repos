package com.melgarejojunior.kotlinrepos.presentation.main

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.melgarejojunior.data.remote.paging.GithubReposPagingSource
import com.melgarejojunior.domain.usecases.PagingSourceWrappedUseCase
import com.melgarejojunior.kotlinrepos.structure.viewmodel.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val wrapperUseCase: PagingSourceWrappedUseCase<GithubReposPagingSource>,
) : ViewModel<MainViewState>(MainViewState.Idle) {

    fun getItems() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 30),
                pagingSourceFactory = { wrapperUseCase.source }
            )
                .flow.cachedIn(viewModelScope)
                .collect {
                }
        }
    }
}