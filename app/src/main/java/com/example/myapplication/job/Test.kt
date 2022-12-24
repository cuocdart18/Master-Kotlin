package com.example.myapplication.job

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
/*fun main() = runBlocking {
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

// ---------------NO USE DELAY-------------
/*fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job ------------- just set isActive is false -------------
    println("main: Now I can quit.")
}*/


// -----------USE FINALLY AFTER COROUTINES IS CANCELED----------------
/*
fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            // Tranh thủ close resource trong này đi nha :D
            println("I'm running finally")
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")
}*/

// -----------COROUTINES CAN DEAD IN FINALLY--------------
/*fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("I'm running finally")
            delay(1000L)    // hàm delay được thêm vào khối finally
//            don't execute this
//            because run delay, coroutines is definitely canceled
//            hàm delay() nó riêng hay tất cả hàm suspend function nói chung có khả
//            năng check xem coroutine còn sống không. Nếu nó đã chết thì tiến trình
//            lập tức bị dừng lại ngay khi chạy vào hàm delay() này
            println("Print me please!")
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")
}*/

// -----------COROUTINES CAN BE IMMORTAL-------------------
/*
fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {  // Nhờ có em NonCancellable mà anh được phép chạy bất chấp đấy
                println("I'm running finally")
                delay(1000L)
                println("I'm non-cancellable")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")
}*/
