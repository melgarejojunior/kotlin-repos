package com.melgarejojunior.kotlinrepos.presentation.di

import com.melgarejojunior.kotlinrepos.presentation.main.di.MainModule
import org.koin.core.module.Module

class FeatureModule {
    fun load(): ArrayList<Module> {
        return arrayListOf(
            MainModule().load()
        )
    }
}