package i_concurrency.coroutines.fundamentals

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main() {

    //blocking()
    //suspending()
}

fun blocking() {
    println("Starting ${Thread.currentThread().name}")
    threadRoutine(1, 500)   // sleeps for 500ms, blocked
    threadRoutine(2, 300)   // sleeps for 300ms, blocked
    Thread.sleep(1000)  // sleeps for 1s for other threads to finish, blocked
    println("Exiting main")
}

private fun threadRoutine(num: Int, sleep: Long) {
    thread {
        println("Starting routine $num on ${Thread.currentThread().name}")
        Thread.sleep(sleep) // blocking call
        println("Exiting routine $num")
    }
}

fun suspending() = runBlocking {
    joinAll(
        // All coroutines running on the main
        launch { coroutine(1, 500) },
        launch { coroutine(2, 300) },

        // Coroutine that runs while other coroutines are suspended
        launch {
            repeat(5) {
                println("Other tasks working on ${Thread.currentThread().name}")
                delay(100)
            }
        }
    )
}

private suspend fun coroutine(num: Int, delay: Long) {
    println("Starting routine $num on ${Thread.currentThread().name}")
    delay(delay) // suspend function, non-blocking
    println("Exiting routine $num")
}
