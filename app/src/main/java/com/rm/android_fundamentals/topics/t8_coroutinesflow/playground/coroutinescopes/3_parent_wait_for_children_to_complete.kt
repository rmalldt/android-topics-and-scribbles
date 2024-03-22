package i_concurrency.coroutines.coroutinescopes

import kotlinx.coroutines.*

fun main() = runBlocking {

    val scope = CoroutineScope(Dispatchers.Default)

    val parentJob = scope.launch {
        launch {
           delay(1000)
           println("Child coroutine 1 completed")
        }

        launch {
            delay(1000)
            println("Child coroutine 2 completed")
        }
    }

    parentJob.join()
    println("Parent coroutine completed")
}


