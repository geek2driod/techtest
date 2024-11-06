package com.techtest.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

object TestAppCoroutineDispatchers {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun create(): AppCoroutineDispatchers {
        return AppCoroutineDispatchers(
            io = UnconfinedTestDispatcher(),
            main = UnconfinedTestDispatcher(),
            default = UnconfinedTestDispatcher()
        )
    }
}