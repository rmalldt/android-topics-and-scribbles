package i_concurrency.coroutines.fundamentals

import kotlinx.coroutines.*

fun main() {

    runCoroutines()
}

fun runCoroutines() = runBlocking {
    joinAll(
        launch { threadSwitchingCoroutine(1, 500) },
        launch { threadSwitchingCoroutine(2, 300) }
    )
}

suspend fun threadSwitchingCoroutine(num: Int, delay: Long) {
    println("Starting routine $num on ${Thread.currentThread().name}") // executes on main thread
    delay(delay)
    withContext(Dispatchers.IO) {   // executes on Dispatchers thread
        println("Exiting routine $num on ${Thread.currentThread().name}")
    }
}






