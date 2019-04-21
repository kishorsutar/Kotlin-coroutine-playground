package com.kishor.kotlin.oroutine

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainVIewModel: ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun callSomething() {
        val job = launch(coroutineContext) {
            delay(1000L)
        }
    }
}