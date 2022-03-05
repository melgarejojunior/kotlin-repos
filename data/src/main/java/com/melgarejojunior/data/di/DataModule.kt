package com.melgarejojunior.data.di

import com.melgarejojunior.data.remote.datasource.ShortenUrlRemoteDataSource
import com.melgarejojunior.data.remote.datasource.ShortenUrlRemoteDataSourceImpl
import com.melgarejojunior.data.repositories.ShortenUrlRepositoryImpl
import com.melgarejojunior.domain.repositories.ShortenUrlRepository
import org.koin.core.module.Module
import org.koin.dsl.module

class DataModule {
    fun load(): Module = module {
    }
}