package i_concurrency.coroutines.coroutinebuilders

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    //runBlockingBlocksUnderlyingThread()
    //runBlockingSuspendFunction()
    //runBlockingChildCoroutine()
}

/**
 * runBlocking starts a new coroutine blocking the underlying thread
 * until the coroutine finishes similar to Thread.sleep().
 */
fun runBlockingBlocksUnderlyingThread() {
    runBlocking {
        launch {
            delay(500)
            println("Printed within coroutine")
        }
    }

    // Notice this statement is outside runBlocking
    // This statement will be blocked until the runBlocking finishes
    println("Exiting main")
}


/**
 * Suspend functions are executed sequentially
 */
fun runBlockingSuspendFunction() = runBlocking {
    delay(3000) // suspend fun
    println("First")
    delay(2000) // suspend fun
    println("Second")
    delay(1000) // suspend fun
    println("Third")
}

/**
 * New coroutines launched inside runBlocking doesn't block.
 */
fun runBlockingChildCoroutine() = runBlocking {
    launch {
        delay(1000)
        println("Printed within launch")
    }

    // Notice this statement is within runBlocking
    // This statement will not be blocked
    println("Printed within runBlocking")
}





