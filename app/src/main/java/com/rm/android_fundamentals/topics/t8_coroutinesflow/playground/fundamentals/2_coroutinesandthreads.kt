package i_concurrency.coroutines.fundamentals

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main() {

    //threadExecution()
    //coroutineExecution()

    //spawnCoroutines()
    //spawnThreads()

}

fun threadExecution() {
    println("Starting ${Thread.currentThread().name}")
    threadRoutine(1, 500)
    threadRoutine(2, 300)
    Thread.sleep(1000)
    println("Exiting main")
}

private fun threadRoutine(num: Int, sleep: Long) {
    thread {
        println("Starting routine $num on ${Thread.currentThread().name}")
        Thread.sleep(sleep) // blocking call
        println("Exiting routine $num")
    }
}

fun coroutineExecution() {
    println("Starting main: ${Thread.currentThread().name}")

    // runBlocking starts new coroutine
    runBlocking {
        joinAll(
            // launch starts new coroutine
            launch { coroutine(1, 500) },
            launch { coroutine(2, 300) }
        )
    }
    println("Exiting main")
}

private suspend fun coroutine(num: Int, delay: Long) {
    println("Starting routine $num on ${Thread.currentThread().name}")
    delay(delay) // suspend function, non-blocking call
    println("Exiting routine $num")
}

fun spawnCoroutines() = runBlocking {
    repeat(1_000_000) {
        launch {
            //delay(5000)
            print(".")
        }
    }
}

fun spawnThreads() {
    repeat(1_000_000) {
        thread {
            print(".")
        }
    }
}
