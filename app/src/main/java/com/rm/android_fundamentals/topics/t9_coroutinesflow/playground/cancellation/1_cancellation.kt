package i_concurrency.coroutines.cancellation

import kotlinx.coroutines.*
import java.lang.Exception

fun main() {

    //cancellation()

    //nonCooperativeCancellation()

    //cooperativeCancellationUsingEnsureActive()
    //cooperativeCancellationUsingYield()
    //cooperativeCancellationManually()

    nonCancelableEx()
}


/**
 * CancellationException is a special exception because like
 * every other exception thrown by coroutine it is also propagated
 * to the parent Job of the coroutine but a coroutine cancelled
 * by throwing Cancellation exception is considered to have been
 * cancelled normally.
 *
 * So, no action is required from parent coroutine. So, the parent
 * won't cancel itself or other children.
 *
 * And it also won't lead to crashing application like other
 * regular uncaught exception would cause.
 *
 * E.g., Delay throws CancellationException.
 *  - If the Job of a coroutine is cancelled or completed while this
 *    function is waiting it immediately resumes with CancellationException.
 *
 * Like delay(), yield(), ensureActive() and other functions also checks if
 * the coroutine in which they're called is still active and also otherwise
 * throws CancellationException.
 *
 * This behaviour of an implementation is called to be Cooperative regarding
 * cancellation.
 */
fun cancellation() = runBlocking {
    val job = launch {
        repeat(10) {
            println("Operation number $it")
            try {
                delay(100)
            } catch (ex: CancellationException) {
                println("CancellationException thrown")
            }
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()
}

fun nonCooperativeCancellation() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) {
            println("Operation number $it")
            Thread.sleep(100)
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()
}

fun cooperativeCancellationUsingEnsureActive() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) {
            ensureActive()
            println("Operation number $it")
            Thread.sleep(100)
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()
}

fun cooperativeCancellationUsingYield() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) {
            yield()
            println("Operation number $it")
            Thread.sleep(100)
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()
}

fun cooperativeCancellationManually() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) {
            if (isActive) {
                println("Operation number $it")
                Thread.sleep(100)
            } else {
                println("Cleaning up...")
                throw CancellationException()
            }
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()
}

fun nonCancelableEx() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) {
            if (isActive) {
                println("Operation number $it")
                Thread.sleep(100)
            } else {
                // Without withContext block, statements below delay()
                // will never be executed since delay() already throws
                // CancellationException and the coroutine returns
                withContext(NonCancellable) {
                    delay(100)
                    println("Cleaning up...")
                    throw CancellationException()
                }
            }
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()
}












