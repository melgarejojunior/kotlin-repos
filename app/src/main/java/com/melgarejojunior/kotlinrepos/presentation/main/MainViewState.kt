package com.melgarejojunior.kotlinrepos.presentation.main

import com.melgarejojunior.kotlinrepos.structure.viewmanagement.ViewState

sealed class MainViewState : ViewState {
    object Idle : MainViewState()
}
