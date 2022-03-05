package com.melgarejojunior.kotlinrepos.structure.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.melgarejojunior.kotlinrepos.structure.viewmanagement.ViewState

open class ViewModel<VS : ViewState>(initialState: VS) : LifecycleObserver,
    ViewModel() {

    val stateLiveData: StateLiveData<VS> = StateLiveData(initialState)

    protected fun updateState(viewStateBlock: (viewState: VS) -> VS) {
        stateLiveData.update(viewStateBlock)
    }
}