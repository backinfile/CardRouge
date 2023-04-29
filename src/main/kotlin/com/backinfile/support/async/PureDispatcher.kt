package com.backinfile.support.async

import com.backinfile.cardRouge.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 单线程方式执行异步代码
 * 当代码流程中出现suspendCoroutine时，控制权暂时转移，等待玩家操作完成执行resume，回到代码流程
 *
 * 如果block中有多个代码块，可能会有问题
 * 如果debug有问题，开启idea设置"Disable coroutine agent"
 */
@OptIn(DelicateCoroutinesApi::class)
fun runAsync(block: suspend CoroutineScope.() -> Unit) {
//    val handler = CoroutineExceptionHandler { _, exception ->
//        Log.game.error("error in run async", exception)
//    }
    val deferred = GlobalScope.launch(PureDispatcher) { block() }
    deferred.start()
}


private object PureDispatcher : CoroutineDispatcher(), CoroutineExceptionHandler {
    @ExperimentalCoroutinesApi
    override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
        throw UnsupportedOperationException("limitedParallelism is not supported for Dispatchers.PureDispatcher")
    }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean = false

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        throw UnsupportedOperationException("Dispatchers.PureDispatcher not dispatched")
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.game.error("error in run async", exception)
    }

    override fun toString(): String = "Dispatchers.PureDispatcher"
}
