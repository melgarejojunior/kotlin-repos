package com.melgarejojunior.kotlinrepos.presentation.main

import android.content.ComponentName
import android.content.Intent
import androidx.paging.PagingSource
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.melgarejojunior.domain.entities.GithubRepository
import com.melgarejojunior.domain.usecases.PagingSourceWrappedUseCase
import com.melgarejojunior.kotlinrepos.presentation.main.viewmodel.MainViewModel
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {
    private val mockedUseCase = mockk<PagingSource<Int, GithubRepository>>(relaxed = true)


    @Before
    fun setup() {
        stopKoin()
        startKoin {}
        loadKoinModules(
            listOf(
                module {
                    viewModel { MainViewModel(PagingSourceWrappedUseCase(mockedUseCase)) }
                }
            )
        )
        launchActivity()
    }

    private fun launchActivity() {
        ActivityScenario.launch<MainActivity>(
            Intent().apply {
                component =
                    ComponentName(ApplicationProvider.getApplicationContext(),
                        MainActivity::class.java)
            }
        )
    }

    @Test
    fun should_ShowAllRepoLoadedOnScreen() {
        TODO("Not implemented yet")
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
