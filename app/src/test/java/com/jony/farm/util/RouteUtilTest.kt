package com.jony.farm.util

import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class RouteUtilTest /*: TestCase()*/ {
    //  private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        //Dispatchers.setMain(mainThreadSurrogate)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
//        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
//        mainThreadSurrogate.close()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testSomeUI() = runBlocking<Unit> {
        val channel = Channel<Int>()
        launch {
            // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
            for (x in 1..5) channel.send(x * x)
        }
// here we print five received integers:
        repeat(5) { println(channel.receive()) }
        println("Done!")


    }
}