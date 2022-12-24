package com.example.myapplication.first

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// -----------INIT THE FIRST COROUTINES WITH LAUNCH()--------------
/*fun main() {
    println("init")
    GlobalScope.launch {
        delay(1000L)
        println("hello ")
    }
    println("world")
    Thread.sleep(5000L)
    println("final")
}*/

// ---------TEST RUN UI------------
/*fun main() {
    println("truoc run")
    runBlocking {
        delay(1000L)
        println("hello")
        delay(1000L)
        println("coroutines")
        delay(2000L)
    }
    println("sau run")
}*/

// ----------TEST 1 TRIEU COROUTINES------------
/*fun main() {
    val start = System.currentTimeMillis()
    var count = 0
    runBlocking {
        repeat(1_000_000) {
            launch { count++ }
        }
    }
    val end = System.currentTimeMillis()
    println("count = $count")
    println("time = ${end - start}")
}*/

