package i_concurrency.coroutines.coroutinebuilders

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

fun main() {

    //asyncIsSequential()
    //accessResultInsideLaunch()
    //accessResultAsync()
    //asyncAwaitEx()
}

/**
 * Like "launch", codes inside "async" are executed sequentially unless
 * new coroutines are started from it.
 */
fun asyncIsSequential() = runBlocking {
    async {
        delay(3000)
        println("First")
        delay(1000)
        println("Second")
        delay(2000)
        println("Third")
    }
}

/**
 * Launch returns Job object, it doesn't return the result of coroutine launched from it.
 */
fun accessResultInsideLaunch() = runBlocking {
    val startTime = System.currentTimeMillis()
    /**
     * Shared mutable state, access and modified from multiple coroutines
     * depending on the type of coroutine dispatchers.
     * Therefore, needs synchronization or thread-safe data structures
     * Best approach is to avoid shared mutable state and use async to return data.
     */
    val results = mutableListOf<String>()   // mutable object, not safe

    // Both calls to launch run in parallel, multiple launch calls
    val job1 = launch {
        val res1 = networkRequest(1, 1000)  // get result
        results.add(res1)                            // access result
        println("Result: $res1 after ${elapsedMillis(startTime)}ms")
    }

    val job2 = launch {
        val res2 = networkRequest(2, 300)   // get result
        results.add(res2)                             // access result
        println("Result: $res2 after ${elapsedMillis(startTime)}ms")
    }

    job1.join()
    job2.join()
    println("Results: $results")
}

/**
 * Async returns Deferred object, a subtype of Job which holds result value
 * returned from the started coroutine from it.
 */
fun accessResultAsync() = runBlocking {// COROUTINE1
    val startTime = System.currentTimeMillis()

    // Both calls to async run in parallel
    val deferred1 = async { // COROUTINE2
        val res1 = networkRequest(1, 1000)
        println("Result: $res1 after ${elapsedMillis(startTime)}ms")
        res1// return result
    }

    val deferred2 = async { // COROUTINE3
        val res2 = networkRequest(2, 300)
        println("Result: $res2 after ${elapsedMillis(startTime)}ms")
        res2// return result
    }

    /**
     * Wait for the result value from started coroutines using async.
     *
     * When an exception occurs in a coroutine started with async, the exception
     * is not thrown directly instead async expects a call to await at some point
     * in the future, so the exception is on hold.
     * Therefore, try-catch block can be only applied around await() call
     */
    try {
        val res1 = deferred1.await() // first suspension point, wait until the result is returned
        val res2 = deferred2.await() // second suspension point, wait until the result is returned
        println("Results: ${listOf(res1, res2)}")
    } catch (e: Exception) {
        throw e
    }
}

fun asyncAwaitEx() = runBlocking {
    val result1 = async {
        networkRequest(1, 3000)
    }.await() // suspend awaiting completion of this value

    // Then this async is started
    val result2 = async {
        networkRequest(2, 1000)
    }.await() // suspend awaiting completion of this value

    println("Results: $result1, $result2")
}

private fun elapsedMillis(time: Long) = System.currentTimeMillis() - time

private suspend fun networkRequest(id: Int, delay: Long): String {
    delay(delay)
    println("Inside network request $id")
    return "res-$id"
}