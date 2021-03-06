package com.kishor.kotlin.oroutine

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainVIewModel: ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun callSomething() {
        println("Print something before launch")
        val job = launch(coroutineContext) {// main coroutine scope
            delay(1000L)
            println("Print something after launch")
        }

    }


    // Using different types of coroutine
     fun coroutineExample() {
        launch(Dispatchers.Main) {// new coroutine runs on Android UI thread
            val result1: Int = withContext(Dispatchers.Default) {// new child context/
                // do something and that will happen in background thread
                //nbdsjdh
                return@withContext 20
            }

            println("Result 1 '$result1'")

            val result2Deferred: Deferred<String> = async(Dispatchers.Default) {// new child context
// do something and that will happen in background thread
                delay(2000L)
                return@async "Result2"
            }

            println("result 2 is not available yet")

            val result2: String = result2Deferred.await()

            println("result 2 is '$result2")
            println("result 3 is 'Tets")
        }
    }

    // Running coroutine sequentially
     fun seq() {
        launch {
            val result1: Deferred<Int> = async(Dispatchers.Default) {
                println("Started first async")
                delay(4000L)
                println("Delayed fnished  async")
                return@async 1
            }

            val result2: Int = withContext(Dispatchers.Default) {
                println("Result 1 can be used here '${result1.await()}")
                return@withContext 2
            }
            println("result 2 is '$result2")
        }
    }

    // running in parallel
     fun par() {
        launch {
            val result1Deferred: Deferred<Int> = async(Dispatchers.Default) {
                // do something
                delay(4000L)
                println("Get result 1 from background")
                return@async 1
            }

            val result2Deferred: Deferred<Int> = async(Dispatchers.Default) {
                // do something
                delay(1000L)
                println("Get result 2 from background")

                return@async 2
            }

            val result1: Int = result1Deferred.await()
            val result2: Int = result2Deferred.await()

            println("Fetched result 1 $result1")
            println("Fetched result 2 $result2")

        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}