object TestDependencies {
    const val junit = "junit:junit:${Versions.junit}"
    const val koin = "io.insert-koin:koin-test:${Versions.koin}"
    const val koinJunit = "io.insert-koin:koin-test-junit4:${Versions.koin}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.testCoroutines}"
    const val androidArchTesting = "androidx.arch.core:core-testing:${Versions.androidArchTesting}"
    const val flowTurbine = "app.cash.turbine:turbine:${Versions.flowTurbine}"
    const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val androidEspresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    const val testCore = "androidx.test:core:${Versions.androidxTest}"
    const val testRunner = "androidx.test:runner:${Versions.androidxTest}"
    const val testRules = "androidx.test:rules:${Versions.androidxTest}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.androidxTestExtJunit}"
}