package com.melgarejojunior.data.di

import androidx.paging.PagingSource
import com.melgarejojunior.data.remote.datasource.GithubRepositoriesRemoteDataSource
import com.melgarejojunior.data.remote.datasource.GithubRepositoriesRemoteDataSourceImpl
import com.melgarejojunior.data.remote.mapper.RepositoryResponseToRepository
import com.melgarejojunior.data.remote.paging.GithubReposPagingSource
import com.melgarejojunior.data.remote.service.GithubRepositoriesService
import com.melgarejojunior.data.util.createService
import com.melgarejojunior.domain.entities.GithubRepository
import com.melgarejojunior.domain.usecases.PagingSourceWrappedUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

class DataModule {
    fun load(): Module = module {
        createService<GithubRepositoriesService>()
        factory { RepositoryResponseToRepository() }
        factory<GithubRepositoriesRemoteDataSource> {
            GithubRepositoriesRemoteDataSourceImpl(service = get())
        }
        factory<PagingSource<Int, GithubRepository>> {
            GithubReposPagingSource(remoteDataSource = get(),
                mapper = get())
        }
        factory { PagingSourceWrappedUseCase<PagingSource<Int, GithubRepository>>(get()) }
    }
}