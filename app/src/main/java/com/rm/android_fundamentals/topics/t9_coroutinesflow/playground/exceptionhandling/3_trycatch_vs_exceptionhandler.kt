package i_concurrency.coroutines.exceptionhandling

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException

fun main() {

    //withTryCatch()

    withCoroutineExceptionHandler()
}

fun withTryCatch() {
    val scope = CoroutineScope(Job())

    scope.launch {
        launch {
            println("Starting coroutine 1")
            delay(100)
            // Exception handled inside coroutine body
            // so the exception doesn't propagate
            try {
                throw RuntimeException()
            } catch (e: Exception) {
                println("Caught exception: $e")
            }
        }

        launch {
            println("Starting coroutine 2")
            delay(2000)
            println("Coroutine 2 completed")
        }
    }

    Thread.sleep(3000)
}

fun withCoroutineExceptionHandler() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(SupervisorJob())

    scope.launch(exceptionHandler) {
        launch {
            println("Starting coroutine 1")
            delay(100)
            throw RuntimeException()
        }

        launch {// this coroutine gets cancelled as the exception propagates
            println("Starting coroutine 2")
            delay(2000)
            println("Coroutine 2 completed")
        }
    }

    Thread.sleep(3000)
}

