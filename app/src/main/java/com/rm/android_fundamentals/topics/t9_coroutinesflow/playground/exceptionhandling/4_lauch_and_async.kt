package i_concurrency.coroutines.exceptionhandling

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException

fun main() {

    //launchException()

    //asyncException()

    //launchAsyncException()

    //asyncAsyncException()

}


fun launchException() {
    val scope = CoroutineScope(Job())

    scope.launch {
        delay(300)
        throw RuntimeException() // exception propagates to scope Job
    }
    Thread.sleep(500)
}

fun asyncException() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    val deferred = scope.async {
        delay(300)
        // When exception is thrown, it propagates to upward but
        // unlike launch doesn't get passed to CoroutineExceptionHandler
        // instead async returns Deferred object which encapsulates
        // the exception. Therefore, only when we receive the actual
        // result the exception is thrown if not handled
        throw RuntimeException()
    }

    scope.launch {
        deferred.await()    // exception is thrown here
    }

    Thread.sleep(500)
}

fun launchAsyncException() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        // Exception propagates upward even though await() not called on Deferred
        // Launch propagates the exception up to scope
        // where the exception is handled.
        async {
            delay(300)
            throw RuntimeException()
        }
    }
    Thread.sleep(500)
}

fun asyncAsyncException() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.async {
        // Exception propagates upward even though await() not called on Deferred
        // Parent async encapsulates the exception and doesn't pass to
        // ExceptionHandler until Deferred.await() is called.
        async {
            delay(300)
            throw RuntimeException()
        }
    }
    Thread.sleep(500)
}
