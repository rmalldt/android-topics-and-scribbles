package i_concurrency.coroutines.coroutinescopes

import kotlinx.coroutines.*

fun main() = runBlocking {

    println("Job of GlobalScope: ${GlobalScope.coroutineContext[Job]}")

    val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        println("Exception caught $throwable")
    }
    val job = GlobalScope.launch(coroutineExceptionHandler) {
        launch {
            delay(50)
            throw RuntimeException()
            println("Still running")
            delay(50)
            println("Still running")
            delay(50)
            println("Still running")
            delay(50)
            println("Still running")
        }.invokeOnCompletion {
            if (it is CancellationException) {
                println("Coroutine cancelled!")
            }
        }
    }
    delay(100)
    job.cancel()
    delay(3000)
}