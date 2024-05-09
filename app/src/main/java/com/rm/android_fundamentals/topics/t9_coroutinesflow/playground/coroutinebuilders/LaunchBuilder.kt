package i_concurrency.coroutines.coroutinebuilders

import kotlinx.coroutines.*

fun main() {

    //launchEx()
    //launchIsSequential()
    //launchWithNestedLaunch()
    //launchAndJoin()
    //launchLazy()
}

/**
 * The "launch" starts a new coroutine without blocking the underlying thread.
 * underlying thread.
 */
fun launchEx() {
    GlobalScope.launch {
        delay(500)
        println("Printed within coroutine") // this won't be printed
    }
    println("Exiting main") // this is printed and the main exits
}   // coroutine delays for 500ms, main exits before the coroutine prints statement


/**
 * Codes inside launch are executed sequentially unless new coroutines are started
 * from it.
 */
fun launchIsSequential() = runBlocking {
    launch {
        println("Started launch")

        // Both network requests are performed on this same coroutine so even if
        // networkRequest1 takes long time, it will block the subsequent code below it
        // until it finishes like a usual sequential execution.
        networkRequest(1, 1000)

        // This request will only run after the above request finishes
        networkRequest(2, 300)

        println("Exiting launch")
    }
}

/**
 * Codes inside launch are executed sequentially unless new coroutines are started
 * from it.
 */
fun launchWithNestedLaunch() = runBlocking {    // COROUTINE1
    launch {// COROUTINE2
        println("Started parent launch")

        // This launch will not block the subsequent code below it
        launch {    // COROUTINE3, nested launch
            delay(3000)
            println("Inside nested launch")
        }

        networkRequest(1, 1000)
        networkRequest(2, 300)
        println("Exiting parent launch")
    }
}

/**
 * Job object returned from launch can be used to manage coroutine such as
 * join and cancel.
 */
fun launchAndJoin() = runBlocking {// COROUTINE1
    val job = launch {// COROUTINE2
        networkRequest(1, 3000)
        println("Result received 1")
    }
    job.join() // COROUTINE2 blocks until it finishes, COROUTINE1 waits for it to finish

    launch {  // COROUTINE3 doesn't block subsequent codes below, no join()
        networkRequest(2, 1000)
        println("Result received 2")
    }

    println("Exiting runBlocking")
}

/**
 * Job object returned from launch can be used to start coroutine lazily.
 */
fun launchLazy() = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {// coroutine2
        networkRequest(1, 500)
        println("Result received")
    }

    delay(1000)
    job.start() // launch coroutine
    job.join()  // coroutine1 waits for the coroutine2 to finish

    println("Exiting runBlocking")
}

private suspend fun networkRequest(num: Int, delay: Long): String {
    delay(delay)
    println("Inside network request $num")
    return "Result $num"
}