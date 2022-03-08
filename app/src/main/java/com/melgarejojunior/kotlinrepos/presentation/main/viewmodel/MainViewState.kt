package com.melgarejojunior.kotlinrepos.presentation.main.viewmodel

import androidx.paging.PagingData
import com.melgarejojunior.domain.entities.GithubRepository
import com.melgarejojunior.kotlinrepos.structure.viewmanagement.ViewState

sealed class MainViewState : ViewState {
    object Idle : MainViewState()
    object Loading : MainViewState()
    class Result(val page: PagingData<GithubRepository>) : MainViewState()
    data class Error(val errorMessageRes: Int) : MainViewState()
}
