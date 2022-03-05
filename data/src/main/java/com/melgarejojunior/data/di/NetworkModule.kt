package com.melgarejojunior.data.di

import com.google.gson.GsonBuilder
import com.melgarejojunior.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule {
    fun load() = module {
        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().serializeNulls().create()
                    )
                )
                .client(provideOkHttpClient())
                .build()
        }
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptors = listOf(
            HttpLoggingInterceptor().setLevel(getLevel()),
        )
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        for (interceptor in interceptors) {
            okHttpClientBuilder.addInterceptor(interceptor)
        }
        return okHttpClientBuilder.build()
    }

    private fun getLevel(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }
}