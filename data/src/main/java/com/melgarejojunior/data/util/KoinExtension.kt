package com.melgarejojunior.data.util

import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import retrofit2.Retrofit

inline fun <reified T> Module.createService(): Pair<Module, InstanceFactory<T>> {
    return factory { get<Retrofit>().create(T::class.java) }
}