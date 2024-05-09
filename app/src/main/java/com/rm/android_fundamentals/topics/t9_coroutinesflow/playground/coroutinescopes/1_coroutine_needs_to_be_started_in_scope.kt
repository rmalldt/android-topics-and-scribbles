package i_concurrency.coroutines.coroutinescopes

import kotlinx.coroutines.*

val mainScope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    println("Starting coroutine from ${Thread.currentThread().name}")

    /**
     * Suppose the mainScope is tied to the main, so when the main exits,
     * this coroutine must be cancelled automatically.
     */
    val job = mainScope.launch {
        println("Coroutine started in ${Thread.currentThread().name}")
        delay(1000)
        println("Coroutine completed")
    }

    job.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Job was cancelled! Coroutine couldn't complete!")
        }
    }

    delay(500)
    onDestroy() // signals end of main scope, triggers cancellation of incomplete jobs if any
    println("Exiting")
}

fun onDestroy() {
    println("Lifetime of main scope ends")
    mainScope.cancel()  // cancel job
}




