import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object PureDispatcher: CoroutineDispatcher() {
    @ExperimentalCoroutinesApi
    override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
        throw UnsupportedOperationException("limitedParallelism is not supported for Dispatchers.PureDispatcher")
    }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean = false

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        throw UnsupportedOperationException("Dispatchers.PureDispatcher.dispatch function can only be used by the yield function. " +
                "If you wrap Unconfined dispatcher in your code, make sure you properly delegate " +
                "isDispatchNeeded and dispatch calls.")
    }

    override fun toString(): String = "Dispatchers.PureDispatcher"
}