package com.example.myapplication.coroutines_scope

import kotlinx.coroutines.*

// ------------DAC DIEM CUA SCOPE TRONG SCOPE--------------
/*fun main() = runBlocking { // scope 1
    coroutineScope { // coroutine 2   // scope 2
        launch {   // coroutine 3
            delay(500L)
            println("Task from nested launch") // line code 2
        }

        delay(100L)
        println("Task from coroutine scope") // line code 3
    }

    launch {       // coroutine 1
        delay(200L)
        println("Task from runBlocking")   // line code 1
    }

    println("Coroutine scope is over") // line code 4
}*/

// ----------------GLOBAL SCOPE KHONG THE BI OUTER SCOPE CANCEL-------------------
/*@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val job = launch {
        // it spawns two other jobs, one with GlobalScope
        GlobalScope.launch {
            println("a")
            delay(1000)
            println("b")  // line code 1 này vẫn được in ra mặc dù bị delay 1000ms
        }
        // and the other inherits the parent context
        launch {
            delay(100)
            println("c")
            delay(1000)
            println("d")
        }
    }
    delay(500)
    job.cancel() // cancel processing of the request
    delay(1000) // delay a second to see what happens
    println("e")
}*/

// ------------DEMO SCOPE AND LIFECYCLE MANAGER---------------------
/*class MyViewModel constructor(private val apiService : ApiService) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    fun executeCalls() {
        launch(context = coroutineContext) {
            val firstRequestDeferred = async {
                apiService.request1()
            }
            val secondRequestDeffered = async {
                apiService.request2()
            }
            handleResponse(firstRequestDeferred,await(),secondRequestDeffered.await())
        }
    }

    override fun onCleared(){
        job.cancel()
    }
}*/

