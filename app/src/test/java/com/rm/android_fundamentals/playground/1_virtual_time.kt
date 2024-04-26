package com.rm.android_fundamentals.playground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SUT {

    suspend fun functionWithDelay(): Int {
        println("Delay 1")
        delay(300)
        println("Delay 2")
        delay(500)
        return 1
    }
}


class TestCase1 {

    @Test
    fun `functionWithDelay should return 1`() = runTest {
        // GIVEN
        val sut = SUT()

        // WHEN
        val actual = sut.functionWithDelay()

        // THEN
        Assertions.assertEquals(1, actual)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class TestCase2 {

    @Test
    fun `functionWithDelay should return 1`() = runTest {
        val realTimeStart = System.currentTimeMillis()
        val virtualTimeStart = currentTime

        val sut = SUT()

        val actual = sut.functionWithDelay()

        Assertions.assertEquals(1, actual)

        val realTimeDuration = System.currentTimeMillis() - realTimeStart
        val virtualTimeDuration = currentTime - virtualTimeStart

        println("Test took: $realTimeDuration real ms")
        println("Test took: $virtualTimeDuration virtual ms")
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class TestCase3 {

    @Test
    fun `functionWithDelay should return 1`() = runTest {
        val realTimeStart = System.currentTimeMillis()
        val virtualTimeStart = currentTime

        launch {
            delay(1000)
            println("Coroutine finished")
        }

        advanceTimeBy(2000) // advance virtual time

        val realTimeDuration = System.currentTimeMillis() - realTimeStart
        val virtualTimeDuration = currentTime - virtualTimeStart
        println("Test took: $realTimeDuration real ms")
        println("Test took: $virtualTimeDuration virtual ms")
    }
}
