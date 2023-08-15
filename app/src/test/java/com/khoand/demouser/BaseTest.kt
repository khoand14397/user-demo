package com.khoand.demouser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
open class BaseTest {
    private val testDispatcher by lazy {
        UnconfinedTestDispatcher()
    }

    @Before
    open fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    open fun after() {
        Dispatchers.resetMain()
    }
}
