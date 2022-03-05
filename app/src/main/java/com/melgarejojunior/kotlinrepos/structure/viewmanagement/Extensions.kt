package com.melgarejojunior.kotlinrepos.structure.viewmanagement

import androidx.appcompat.app.AppCompatActivity
import com.melgarejojunior.kotlinrepos.structure.viewmodel.ViewModel

fun <VS : ViewState> AppCompatActivity.onStateChanged(
    viewModel: ViewModel<VS>,
    block: (state: VS) -> Unit,
) {
    viewModel.stateLiveData.liveData.observe(this) { block(it) }
}