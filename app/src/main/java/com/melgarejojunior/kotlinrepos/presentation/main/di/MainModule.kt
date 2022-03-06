package com.melgarejojunior.kotlinrepos.presentation.main.di

import com.melgarejojunior.kotlinrepos.presentation.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MainModule {
    fun load() = module {
        viewModel { MainViewModel(get()) }
    }
}