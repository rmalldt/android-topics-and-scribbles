package i_concurrency.coroutines.exceptionhandling

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException

fun main() {

    //catchingException()

    //uncaughtException1()
    //handlingExceptionUsingTryCatch()

    uncaughtException2()
    //handlingExceptionUsingTryCatchNested()
}

val scope = CoroutineScope(Job())

fun uncaughtException1() {
    scope.launch {
        functionThatThrows() // throws exception, no try-catch
    }

    Thread.sleep(300)
}

fun handlingExceptionUsingTryCatch() {
    scope.launch {
        // Exception handled inside the coroutine body
        try {
            functionThatThrows()
        } catch (ex: Exception) {
            println("Caught exception: $ex")
        }
    }

    Thread.sleep(300)
}

fun uncaughtException2() {
    scope.launch {
        println("Starting parent coroutine")
        try {
            launch {
                println("Starting child coroutine")
                // The exception thrown is not caught by outer try-catch
                // since it is not rethrown unlike regular function
                // instead is propagated up the job hierarchy
                delay(100)
                functionThatThrows()
            }
        } catch (ex: Exception) {
            println("Caught exception: $ex")
        }
    }
    Thread.sleep(1000)
}

fun handlingExceptionUsingTryCatchNested() {
    scope.launch {
        println("Starting parent coroutine")
        try {
            launch {
                //Exception handled inside the coroutine body
                try {
                    println("Starting child coroutine")
                    functionThatThrows()
                } catch (ex: Exception) {
                    println("Caught exception: $ex")
                }
            }
        } catch (ex: Exception) {
            println("Caught exception: $ex")
        }
    }

    Thread.sleep(1000)
}

fun catchingException() {
    try {
        functionThatThrows()
    } catch (ex: Exception) {
        println("Exception caught: $ex")
    }
}

private fun functionThatThrows() {
    throw RuntimeException()
}
