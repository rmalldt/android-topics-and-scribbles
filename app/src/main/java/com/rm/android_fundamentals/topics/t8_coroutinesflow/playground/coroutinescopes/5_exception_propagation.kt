package i_concurrency.coroutines.coroutinescopes

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() {

    //jobExceptionPropagatesUpwards()

    supervisorJobExceptionDoNotPropagate()

}

fun jobExceptionPropagatesUpwards() {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception $throwable")
    }

    // If no Job provided, scope creates Job() by default
    val scope = CoroutineScope(Dispatchers.Default + exceptionHandler)

    scope.launch {
        println("Coroutine 1 started")
        delay(50)
        println("Coroutine 1 fails!")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 started")
        delay(500)
        println("Coroutine 2 completed!")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 cancelled!")
        }
    }

    Thread.sleep(1000)
    println("Scope is alive: ${scope.isActive}")
}



fun supervisorJobExceptionDoNotPropagate() {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception $throwable")
    }

    // Scope with SupervisorJob()
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default + exceptionHandler)

    scope.launch {
        println("Coroutine 1 started")
        delay(50)
        println("Coroutine 1 fails!")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 started")
        delay(500)
        println("Coroutine 2 completed!")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 cancelled!")
        }
    }

    Thread.sleep(1000)
    println("Scope is alive: ${scope.isActive}")
}

