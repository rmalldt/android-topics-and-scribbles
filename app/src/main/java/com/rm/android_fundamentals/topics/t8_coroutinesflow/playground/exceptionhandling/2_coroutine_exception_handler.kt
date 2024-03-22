package i_concurrency.coroutines.exceptionhandling

import kotlinx.coroutines.*
import kotlin.Exception
import kotlin.RuntimeException

fun main() {

    usingCoroutineExceptionHandler1()
    //usingCoroutineExceptionHandler2()
    //usingCoroutineExceptionHandlerNested()
    //exceptionHandlerShouldBeUsedOnlyInScopeOrToLevelCoroutine()

}

fun usingCoroutineExceptionHandler1() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job() + exceptionHandler) // install handler in scope

    scope.launch {
        throw RuntimeException()
    }
    Thread.sleep(3000)
}

fun usingCoroutineExceptionHandler2() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job())

    scope.launch(exceptionHandler) {// install handler in launch
        throw RuntimeException()
    }
    Thread.sleep(100)
}

fun usingCoroutineExceptionHandlerNested() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        launch {    // nested coroutine
            throw RuntimeException()
        }
    }
    Thread.sleep(100)
}

fun exceptionHandlerShouldBeUsedOnlyInScopeOrToLevelCoroutine() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job())

    scope.launch {
        launch(exceptionHandler) {// this handler has effect
            throw RuntimeException()
        }
    }
    Thread.sleep(100)
}





