package com.rm.android_fundamentals.topics.t8_coroutinesflow.playground.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

fun main() = runBlocking {
    launch {
        delay(2000L)
        println("World")
    }
    println("Hello")
    test()
}






private fun test() {
    val list = mutableListOf("Tim", "Gina", "Kale")

    list.remove("Gina")
    list.forEach { println(it) }
}





suspend fun backgroundTask(param: Int) {
    // long running task
}

// Under the hood, the above suspend function is converted by the compiler to
// a regular function like this:
fun backgroundTask(param: Int, callback: Continuation<Int>) {
    // long running task
}

interface Continuation<in T> {
    val context: CoroutineContext
    fun resume(value: T)
    fun resumeWithException(e: Throwable)
}