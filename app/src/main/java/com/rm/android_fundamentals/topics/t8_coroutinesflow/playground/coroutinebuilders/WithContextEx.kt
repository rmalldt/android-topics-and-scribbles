package i_concurrency.coroutines.coroutinebuilders

import kotlinx.coroutines.*

fun main() {
    //withContextBlock()
}

fun withContextBlock() = runBlocking {

    launch {
        delay(1000)
        println("launch 1")
    }

    launch {
        delay(50)
        println("launch 2")
    }


    val res = withContext(Dispatchers.Default) {
        delay(3000)
        "Hello"
    }
    println(res)

    launch {
        delay(50)
        println("launch 3")
    }
}