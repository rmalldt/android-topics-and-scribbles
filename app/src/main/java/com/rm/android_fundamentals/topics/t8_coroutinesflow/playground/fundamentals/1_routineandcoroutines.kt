package i_concurrency.coroutines.fundamentals

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    //synchronousExecution()
    //asynchronousExecution()
}

fun synchronousExecution() {
    // Execution follows the order of instructions
    println("Starting main")
    routine(1, 500)
    routine(2, 300)
    println("Exiting main")
}

private fun routine(num: Int, sleep: Long) {
    println("Starting routine $num")
    Thread.sleep(sleep)
    println("Exiting routine $num")
}

fun asynchronousExecution() {
    // Execution doesn't follow the order of instructions
    println("Starting main")

    // Run blocking starts a new coroutine
    runBlocking {
        joinAll(
            // launch starts a new coroutine
            launch { coroutine(1, 500) },
            launch { coroutine(2, 300) }
        )
    }
    println("Exiting main")
}

private suspend fun coroutine(num: Int, delay: Long) {
    println("Starting routine $num")
    delay(delay)
    println("Exiting routine $num")
}