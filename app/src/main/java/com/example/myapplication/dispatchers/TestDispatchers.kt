package com.example.myapplication.dispatchers

import android.util.Log
import kotlinx.coroutines.*

object TestDispatchers {

    fun runMyFirstCoroutine() {
        // default mode
        GlobalScope.launch(Dispatchers.Default) {
            Log.d(
                MainActivity::class.java.simpleName,
                "dispatchers default run on ${Thread.currentThread().name}"
            )
        }

        // IO mode
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(
                MainActivity::class.java.simpleName,
                "dispatchers IO run on ${Thread.currentThread().name}"
            )
        }
        // main mode
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000L)
            Log.d(
                MainActivity::class.java.simpleName,
                "dispatchers Main run on ${Thread.currentThread().name}"
            )
        }
        // unconfined mode
        GlobalScope.launch(Dispatchers.Unconfined) {
            Log.d(
                MainActivity::class.java.simpleName,
                "dispatchers Unconfined run on ${Thread.currentThread().name}"
            )
            delay(2000L)
            Log.d(
                MainActivity::class.java.simpleName,
                "dispatchers Unconfined run on ${Thread.currentThread().name}"
            )
        }
        // another thread from dev
        GlobalScope.launch(newSingleThreadContext("my_thread")) {
            Log.d(
                MainActivity::class.java.simpleName,
                "dispatchers newThread run on ${Thread.currentThread().name}"
            )
        }
    }
}