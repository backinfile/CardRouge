package com.backinfile.support.async

import kotlinx.coroutines.*
import kotlinx.coroutines.future.asCompletableFuture
import kotlin.coroutines.CoroutineContext

@OptIn(DelicateCoroutinesApi::class)
fun runAsync(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.async(PureDispatcher) { block() }.asCompletableFuture()
}

object PureDispatcher: CoroutineDispatcher() {
    @ExperimentalCoroutinesApi
    override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
        throw UnsupportedOperationException("limitedParallelism is not supported for Dispatchers.PureDispatcher")
    }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean = false

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        throw UnsupportedOperationException("Dispatchers.PureDispatcher not dispatched")
    }

    override fun toString(): String = "Dispatchers.PureDispatcher"
}
