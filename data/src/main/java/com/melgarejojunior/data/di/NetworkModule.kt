package com.melgarejojunior.data.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.melgarejojunior.data.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    fun load() = module {
        single {
            provideOkHttpClient(androidContext())
        }
        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().serializeNulls().create()
                    )
                )
                .client(get())
                .build()
        }
    }

    private fun provideOkHttpClient(androidContext: Context): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB

        val cache = Cache(androidContext.cacheDir, cacheSize)

        val interceptors = listOf(
            HttpLoggingInterceptor().setLevel(getLevel()),
        )
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        for (interceptor in interceptors) {
            okHttpClientBuilder.addInterceptor(interceptor)
        }
        return okHttpClientBuilder.build()
    }

    private fun getLevel(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }
}