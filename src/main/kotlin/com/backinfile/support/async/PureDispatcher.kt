package com.backinfile.support.async

import kotlinx.coroutines.*
import kotlinx.coroutines.future.asCompletableFuture
import kotlin.coroutines.CoroutineContext

/**
 * 单线程方式执行异步代码
 * 当代码流程中出现suspendCoroutine时，控制权暂时转移，等待玩家操作完成执行resume，回到代码流程
 *
 * 如果block中有多个代码块，可能会有问题
 */
@OptIn(DelicateCoroutinesApi::class)
fun runAsync(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.async(PureDispatcher) { block() }.asCompletableFuture()
}

object PureDispatcher : CoroutineDispatcher() {
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
