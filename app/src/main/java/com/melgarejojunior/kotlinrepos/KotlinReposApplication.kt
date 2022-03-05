package com.melgarejojunior.kotlinrepos

import android.app.Application
import com.melgarejojunior.data.di.DataModule
import com.melgarejojunior.data.di.NetworkModule
import com.melgarejojunior.kotlinrepos.presentation.di.FeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KotlinReposApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@KotlinReposApplication)
            modules(
                listOf(
                    NetworkModule().load(),
                    DataModule().load(),

                ) + FeatureModule().load()
            )
        }
    }
}