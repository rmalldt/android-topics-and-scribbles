package i_concurrency.coroutines.coroutinescopes

import kotlinx.coroutines.*

fun main() {

    //cancellingParentCancelsAllChildren()
    cancellingChildDoNotCancelParent()
}

fun cancellingParentCancelsAllChildren() = runBlocking {
    // Scope with parent job
    val scope = CoroutineScope(Dispatchers.Default)

    // COROUTINE 1
    scope.launch {
        delay(50)
        println("Coroutine 1 completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 1 cancelled!")
        }
    }

    // COROUTINE 2
    scope.launch {
        delay(1000)
        println("Coroutine 2 completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 cancelled!")
        }
    }

    // Cancelling parent scope
    scope.coroutineContext[Job]!!.cancelAndJoin()
}

fun cancellingChildDoNotCancelParent() = runBlocking {
    // Scope with parent job
    val scope = CoroutineScope(Dispatchers.Default)

    scope.coroutineContext[Job]!!.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Parent scope cancelled!")
        }
    }

    // COROUTINE 1
    val job1 = scope.launch {
        delay(50)
        println("Coroutine 1 completed")
    }

    job1.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 1 cancelled!")
        }
    }

    // COROUTINE 2
   scope.launch {
        delay(1000)
       println("Coroutine 2 completed!")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 cancelled!")
        }
    }

    // Cancelling child coroutine
    job1.cancelAndJoin()
}
