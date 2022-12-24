package com.example.myapplication.withContext

import android.util.Log
import com.example.myapplication.dispatchers.MainActivity
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object TestWithContext {

    fun testMyFirstWithContext() {
        newSingleThreadContext("Thread_1").use { context1 ->
            Log.d(
                MainActivity::class.java.simpleName,
                "Context 1 - Thread: ${Thread.currentThread().name}"
            )
            newSingleThreadContext("Thread_2").use { context2 ->
                Log.d(
                    MainActivity::class.java.simpleName,
                    "Context 2 - Thread: ${Thread.currentThread().name}"
                )

                runBlocking(context1) {
                    Log.d(
                        MainActivity::class.java.simpleName,
                        "Working in context 1 - Thread: ${Thread.currentThread().name}"
                    )
                    // switch context
                    withContext(context2) {
                        Log.d(
                            MainActivity::class.java.simpleName,
                            "Working in context 2 - Thread: ${Thread.currentThread().name}"
                        )
                    }
                    // back to context 1
                    Log.d(
                        MainActivity::class.java.simpleName,
                        "Back to context 1 - Thread: ${Thread.currentThread().name}"
                    )
                }
            }
        }
    }
}