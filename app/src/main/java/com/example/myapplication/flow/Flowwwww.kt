package com.example.myapplication.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.system.measureTimeMillis

// ---------------SEQUENCES BLOCK THREAD-----------------
/*fun foo(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
        Thread.sleep(1000)
        yield(i) // yield next value
    }
}

fun main() = runBlocking {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        println(Thread.currentThread().name)
        for (k in 1..3) {
            delay(1000)
            println("I'm blocked $k")
        }
    }
    val time = measureTimeMillis {
        foo().forEach { value -> println(value) }
    }
    println("$time s")
}*/

// ------------------USE FLOW----------------------
/*fun foo(): Flow<Int> = flow {
    // flow builder
    for (i in 1..3) {
        delay(1000)
        emit(i) // emit next value
    }
}

fun main() = runBlocking {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        println(Thread.currentThread().name)
        for (k in 1..3) {
            delay(900)
            println("I'm not blocked $k")
        }
    }
    // Collect the flow
    val time = measureTimeMillis {
        foo().collect { value -> println(value) }
    }
    println("$time s")
}*/

// --------------FLOW IS A COLD STREAM---------------
/*fun foo(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    println("Calling foo...")
    val flow = foo()
    println("Calling collect...")
    flow.collect { value -> println(value) }
    println("Calling collect again...")
    flow.collect { value -> println(value) }
}*/

// ----------FLOW BE CANCELED-------------
/*fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(2000)
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    withTimeoutOrNull(5000) { // Timeout after 5s
        foo().collect { value -> println(value) }
    }
    println("Done")
}*/

/*fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        Thread.sleep(2000)
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    withTimeout(1000) { // Timeout after 1s
        foo().collect { value -> println(value) }
    }

    println("Done")
}*/

// ------------CREATE FLOW--------------
/*fun main() = runBlocking {
    val data = flowOf(1,"abc", 3.4, "def")
    data.collect { println(it) }
}*/

// -----------CONVERT TO FLOW---------------
/*fun main() = runBlocking {
    listOf(1, "abc", 3.4, "def").asFlow().collect { println(it) }
}*/

