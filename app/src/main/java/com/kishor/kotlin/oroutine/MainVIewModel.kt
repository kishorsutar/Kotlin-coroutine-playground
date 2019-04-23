package com.kishor.kotlin.oroutine

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainVIewModel: ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun callSomething() {
        val job = launch(coroutineContext) {// main coroutine scope
            delay(1000L)
        }
    }


    // Using different types of coroutine
    private fun coroutineExample() {
        launch(Dispatchers.Main) {// new coroutine runs on Android UI thread
            val result1: Int = withContext(Dispatchers.Default) {// new child context/
                // do something and that will happen in background thread
                return@withContext 20
            }

            println("Result 1 '$result1'")

            val result2Deferred: Deferred<String> = async(Dispatchers.Default) {// new child context
// do something and that will happen in background thread
                return@async "Result2"
            }

            println("result 2 is not available yet")

            val result2: String = result2Deferred.await()

            println("result 2 is '$result2")
        }
    }

    // Running coroutine sequentially
    private fun seq() {
        launch {
            val result1: Int = withContext(Dispatchers.Default) {
                return@withContext 1
            }

            val result2: Int = withContext(Dispatchers.Default) {
                println("Result 1 can be used here '$result1")
                return@withContext 2
            }

        }
    }

    // running in parallel
    private fun par() {
        launch {
            val result1Deferred: Deferred<Int> = async(Dispatchers.Default) {
                // do something
                return@async 1
            }

            val result2Deferred: Deferred<Int> = async(Dispatchers.Default) {
                // do something
                return@async 2
            }

            val result1: Int = result1Deferred.await()
            val result2: Int = result2Deferred.await()
        }
    }

}