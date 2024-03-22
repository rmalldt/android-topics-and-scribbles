package i_concurrency.coroutines.coroutinescopes

import kotlinx.coroutines.*

fun main() {

    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

    var childCoroutineJob: Job? = null
    val coroutineJob = scope.launch {
        childCoroutineJob = launch {
            println("Starting child coroutine")
            delay(300)
        }
        println("Starting coroutine")
        delay(1000)
    }

    Thread.sleep(50)
    println("childCoroutineJob is a child of coroutineJob: ${coroutineJob.children.contains(childCoroutineJob)}")
    println("coroutineJob is a child of scopeJob: ${scopeJob.children.contains(coroutineJob)}")
}