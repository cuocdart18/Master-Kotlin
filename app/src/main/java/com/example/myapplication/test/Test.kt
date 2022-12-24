package com.example.myapplication.test

import kotlinx.coroutines.*

// --------------JOIN--------------
/*fun main() = runBlocking {
    val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
        delay(5000L)
        println("World!")
    }
    println("Hello,")
    job.join() // wait until child coroutine completes
    println("Kotlin")
}*/

// -------------CANCEL-----------------
/*
fun main() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")
}*/
