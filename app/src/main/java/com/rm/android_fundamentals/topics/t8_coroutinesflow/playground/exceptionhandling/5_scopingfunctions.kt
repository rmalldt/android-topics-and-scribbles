package i_concurrency.coroutines.exceptionhandling

import kotlinx.coroutines.*
import kotlin.Exception

import kotlin.RuntimeException

fun main() {

    //unhandledException()

    //coroutineScopeFun()

    //supervisorScopeFun1()
    //supervisorScopeRethrows()
    //supervisorScopeAndLaunch()
    //supervisorScopeAndAsyncAwaitCalledInScope()
    //supervisorScopeAndAsyncAwaitCalledInCoroutine()

    //ifOneFailsCancelAllSiblings()
    ifOneFailsResumeOtherSiblings()

}


fun unhandledException() = runBlocking {
    try {
        // Exception is propagated not rethrown so can't be caught by
        // outer try-catch block. Or handle exception inside the launch block.
        launch {
            throw RuntimeException()
        }
    } catch (ex: Exception) {
        println("Caught exception: $ex")
    }
}


fun coroutineScopeFun() = runBlocking {
    try {
        // coroutineScope propagates the exception up while also
        // rethrowing the exception, so can be caught by outer try-catch block
        coroutineScope {
            launch {
                throw RuntimeException()
            }
        }
    } catch (ex: Exception) {
        println("Caught exception: $ex")
    }
}

fun supervisorScopeFun1() = runBlocking {
    try {
        // Exception is not rethrown and not propagated
        // since supervisorScope has supervisorJob
        // Hence, can't be caught using try-catch block
        // Use CoroutineExceptionHandler to handle it
        supervisorScope {
            launch {
                throw RuntimeException()
            }
        }
    } catch (ex: Exception) {
        println("Caught exception: $ex")
    }
}

fun supervisorScopeRethrows() = runBlocking {
    try {
        // Exception directly thrown by the supervisorScope not its child
        // so the exception is rethrown and therefore can be caught.
        supervisorScope {
            throw RuntimeException()
        }
    } catch (ex: Exception) {
        println("Caught exception: $ex")
    }
}

fun supervisorScopeAndLaunch() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    try {
        // Exception is not rethrown and not propagated since
        // supervisorScope has supervisorJob
        // Hence, can't be caught using try-catch block
        // Therefore, CoroutineExceptionHandler is used to handle it.
        // Or handle the exception inside coroutine.
        supervisorScope {
            launch(exceptionHandler) {
                throw RuntimeException()
            }
        }
    } catch (ex: Exception) {
        println("Caught exception: $ex")
    }
}

fun supervisorScopeAndAsyncAwaitCalledInScope() = runBlocking {
    try {
        supervisorScope {
            val deferred = async {
                throw RuntimeException()
            }

            // Exception is rethrown since await is directly called inside
            // supervisorScope, not in the coroutine started by supervisorScope.
            deferred.await()
        }
    } catch (ex: Exception) {
        println("Caught exception: $ex")
    }
}

fun supervisorScopeAndAsyncAwaitCalledInCoroutine() = runBlocking {
    try {
        supervisorScope {
            val deferred = async {
                throw RuntimeException()
            }

            // Exception is not rethrown since await is called inside the child
            // coroutine of supervisorScope
            // Use CoroutineExceptionHandler
            launch {
                deferred.await()
            }
        }
    } catch (ex: Exception) {
        println("Caught exception: $ex")
    }
}

fun ifOneFailsCancelAllSiblings() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    scope.launch {
        val def1 = async {
            println("async1 started")
            throw RuntimeException()    // exception!
        }

        val def2 = async {
            println("async2 started")
            "hello"
        }

        val def3 = async {
            println("async3 started")
            "world"
        }

        var num = 0
        val resultList = listOf(def1, def2, def3)
            .map { def ->
                try {
                    ++num
                    def.await()
                } catch (ex: Exception) {
                    println("Caught $ex in def$num")
                    null
                }
            }
        println(resultList)
    }

    Thread.sleep(3000)
}

fun ifOneFailsResumeOtherSiblings() = runBlocking {
    val scope = CoroutineScope(SupervisorJob())

    scope.launch {
        supervisorScope {
            /**
             * Parent has SupervisorJob therefore, if any async call throws exception
             * other siblings will not get cancelled.
             */
            val def1 = async {
                println("async1 started")
                throw RuntimeException()
            }

            val def2 = async {
                println("async2 started")
                "hello"
            }

            val def3 = async {
                println("async3 started")
                "world"
            }

            var num = 0

            /**
             * The exception thrown by await must be handled using try catch
             * otherwise the supervisorScope will rethrow the exception directly
             * to threads UncaughtExceptionHandler since it doesn't propagate exception
             */
            val resultList = listOf(def1, def2, def3)
                .map { def ->
                    ++num
                    //try {
                        def.await()
                    /*} catch (ex: Exception) {
                        println("Caught $ex in def$num")
                        null
                    }*/
                }
            println(resultList)
        }
    }

    Thread.sleep(3000)
}

