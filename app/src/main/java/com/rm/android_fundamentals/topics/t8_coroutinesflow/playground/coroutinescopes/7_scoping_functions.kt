package i_concurrency.coroutines.coroutinescopes

import kotlinx.coroutines.*

fun main() {

    //usingJoin()

    //usingNestedBlock()

    //usingCoroutineScoping1()
    usingCoroutineScoping2()

}


fun usingCoroutineScoping2() {
    val scope = CoroutineScope(Job())

    scope.launch {

        doSomeTask() // suspends, await completion

        launch {
            println("Staring coroutine 3")
            delay(300)
            println("Coroutine 3 completed")
        }
    }

    Thread.sleep(1000)
}

// Only returns when all of its child coroutines have finished
suspend fun doSomeTask() = supervisorScope {
    launch {
        println("Staring coroutine 1")
        delay(100)
        println("Coroutine 1 completed")
    }
    launch {
        println("Staring coroutine 2")
        delay(200)
        println("Coroutine 2 completed")
    }
}

fun usingCoroutineScoping1() {
    val scope = CoroutineScope(Job())

    scope.launch {

        // Only returns when all of its child coroutines have finished
        coroutineScope{
            launch {
                println("Staring coroutine 1")
                delay(100)
                println("Coroutine 1 completed")
            }
            launch {
                println("Staring coroutine 2")
                delay(200)
                println("Coroutine 2 completed")
            }
        }

        launch {
            println("Staring coroutine 3")
            delay(300)
            println("Coroutine 3 completed")
        }
    }

    Thread.sleep(1000)
}


fun usingNestedBlock() {
    val scope = CoroutineScope(Job())

    scope.launch {
        val job1 = launch {
            launch {
                println("Staring coroutine 1")
                delay(100)
                println("Coroutine 1 completed")
            }
            launch {
                println("Staring coroutine 2")
                delay(200)
                println("Coroutine 2 completed")
            }
        }
        job1.join()

        launch {
            println("Staring coroutine 3")
            delay(300)
            println("Coroutine 3 completed")
        }
    }

    Thread.sleep(1000)
}

fun usingJoin() {
    val scope = CoroutineScope(Job())

    scope.launch {
        val job1 = launch {
            println("Staring coroutine 1")
            delay(100)
            println("Coroutine 1 completed")
        }
        val job2 = launch {
            println("Staring coroutine 2")
            delay(200)
            println("Coroutine 2 completed")
        }
        job1.join()
        job2.join()

        launch {
            println("Staring coroutine 3")
            delay(300)
            println("Coroutine 3 completed")
        }
    }
    Thread.sleep(1000)
}
