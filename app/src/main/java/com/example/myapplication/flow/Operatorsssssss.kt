package com.example.myapplication.flow

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CancellationException

// -------------TAKE()--------------
/*fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
    } catch (e: CancellationException) {
        println("exception")
    } finally {
        println("close resource here")
    }
}

fun main() = runBlocking {
    numbers()
        .take(2) // take only the first two
        .collect { value -> println(value) }
}*/

// ------------TRANSFORM()----------------
/*fun main() = runBlocking {
    (1..9).asFlow() // a flow of requests
        .transform { value ->
            if (value % 2 == 0) { // Emit only even values, but twice
                emit(value * value)
                emit(value * value * value)
            } // Do nothing if odd
        }
        .collect { response -> println(response) }
}*/

// -------------MAP()-----------------
/*fun main() = runBlocking {
    (1..3).asFlow()
        .map { it * it } // squares of numbers from 1 to 5
        .collect { println(it) }
}*/

// -------------FILTER()-------------
/*fun main() = runBlocking {
    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it % 2 == 0
        }.collect {
            println("Collect $it")
        }
}*/

// ------------ONEACH()---------------
/*fun main() = runBlocking {
    val nums = (1..3).asFlow().onEach { delay(3000) } // numbers 1..3 every 300 ms
    val startTime = System.currentTimeMillis()
    nums.collect { value ->
        println("$value at ${System.currentTimeMillis() - startTime} ms from start")
    }
}*/

// ----------------REDUCE()------------
/*fun main() = runBlocking {
    val sum = listOf("a", "b", "c", "d", "e").asFlow()
        .reduce { a, b ->
        // a is old list
        // b is new element
            println("Tổng đã tích lũy: $a")
            println("Giá trị mới: $b")
            a + b }
    println("Kết quả = $sum")
}*/

// -------------FOLD()-------------
/*fun main() = runBlocking {
    val sum = (1..3).asFlow()
        .fold(initial = 10) { a, b -> // mình cho giá trị khởi tạo ban đầu là 10
            println("Tổng đã tích lũy: $a đồng")
            println("Giá trị mới: $b đồng")
            a + b } // sum them (terminal operator)
    println("Kết quả = $sum đồng")
}*/

// ------------ToLIST() ToSET()--------------
/*fun main() = runBlocking {
    val list: List<String> = listOf("a", "b", "c", "d", "e").asFlow().toList()
    val set: Set<Int> = (1..5).asFlow().toSet()
    println("${list.javaClass} $list")
    println("${set.javaClass} $set")
}*/

// -------------FIRST()-------------
/*fun main() = runBlocking {
    val a: Int = listOf(1, 3, 5, 7, 2, 6, 8, 4).asFlow().first { it % 2 == 0 } // in ra số chẵn đầu tiên
    println(a)
}*/

// --------------SINGLE()----------------
/*fun main() = runBlocking {
    val a: Int? = listOf(10).asFlow().singleOrNull()    // trả về 10
    val b: Int? = listOf<Int>().asFlow().singleOrNull() // trả về null vì có ít hơn 1 phần tử
    listOf(1, 2).asFlow().singleOrNull()                // throw Exception vì có nhiều hơn 1 phần tử
    println(a.toString()) // in ra 10
    println(b.toString()) // in ra null
}*/

// --------------ZIP()----------------
/*fun main() = runBlocking<Unit> {
    val nums = (1..3).asFlow() // numbers 1..3
    val strs = flowOf("one", "two", "three") // strings
    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
        .collect { println(it) } // collect and print
}*/

// --------------COMBINE()--------------
// with zip
/*fun main() = runBlocking {
    val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
    val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
    val startTime = System.currentTimeMillis() // remember the start time

    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string with "zip"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}*/

// with combine
/*fun main() = runBlocking {
    val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
    val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
    val startTime = System.currentTimeMillis() // remember the start time

    nums.combine(strs) { a, b -> "$a -> $b" } // compose a single string with "combine"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }

}*/

// ---------------flatMap()-------------------
// flatMapConCat()
/*fun requestFlow(i: Int): Flow<String> = flow { // Đây là flowB
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis() // remember the start time
    // Dưới đây là flowA
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
        .flatMapConcat { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}*/

// flatMapMerge
/*fun requestFlow(i: Int): Flow<String> = flow { // Đây là flowB
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis() // remember the start time
    // Dưới đây là flowA
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
        .flatMapMerge { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}*/

// flatMapLatest
/*fun requestFlow(i: Int): Flow<String> = flow { // Đây là flowB
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis() // remember the start time
    // Dưới đây là flowA
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
        .flatMapLatest { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}*/



