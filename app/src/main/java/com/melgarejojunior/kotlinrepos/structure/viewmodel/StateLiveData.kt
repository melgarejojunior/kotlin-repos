package com.melgarejojunior.kotlinrepos.structure.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.melgarejojunior.kotlinrepos.structure.viewmanagement.ViewState

class StateLiveData<VS : ViewState>(viewState: VS) {
    private val _viewState: MutableLiveData<VS> = MutableLiveData<VS>().apply { value = viewState }
    val liveData: LiveData<VS> get() = _viewState

    fun update(viewStateBlock: (viewState: VS) -> VS) {
        _viewState.postValue(viewStateBlock(_viewState.value!!))
    }

}