package com.melgarejojunior.kotlinrepos.presentation.main.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.melgarejojunior.data.remote.exceptions.HttpException
import com.melgarejojunior.domain.entities.GithubRepository
import com.melgarejojunior.domain.usecases.PagingSourceWrappedUseCase
import com.melgarejojunior.kotlinrepos.R
import com.melgarejojunior.kotlinrepos.structure.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    private val wrapperUseCase: PagingSourceWrappedUseCase<PagingSource<Int, GithubRepository>>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val pageSize: Int = 30,
) : ViewModel<MainViewState>(MainViewState.Idle) {

    init {
        getItems()
    }

    private fun getItems() {
        viewModelScope.launch(dispatcher) {
            Pager(
                config = PagingConfig(pageSize),
                pagingSourceFactory = { wrapperUseCase.source }
            )
                .flow.cachedIn(viewModelScope)
                .flowOn(dispatcher)
                .onStart { updateState { MainViewState.Loading } }
                .catch { handleError(it) }
                .collect { pagingData ->
                    updateState {
                        MainViewState.Result(pagingData)
                    }
                }
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is IOException -> R.string.io_error_message
            is HttpException -> R.string.http_error_message
            else -> R.string.generic_error_message
        }
    }
}