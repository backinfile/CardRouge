import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


fun main() = runBlocking {
    launch { ServerA().start() }
    launch { ServerB().start() }
    Unit
}

open class Service {
    private val name: String by lazy { this.javaClass.simpleName }
    private val context by lazy { newSingleThreadContext(this.name) }
    private var time = 0L

    suspend fun start() {
        println("in suspend")
        withContext(context) {
            while (true) {
                delay(1000)
                update(time)
                time += 1000
            }
        }
    }

    open suspend fun update(mills: Long) {
        println("update in $name")
    }

    companion object {
        @JvmStatic
        protected val requests by lazy { ConcurrentLinkedQueue<RPC>() }
        fun addContinuation(continuation: Continuation<Result>, block: () -> Any?) {

        }

        suspend fun <T> wrap( block:  () -> T): T {
            println("wrap before ${Thread.currentThread().name}")
            val result = suspendCoroutine<Result> {
                requests.add(RPC(it) { block() })
            }
            println("wrap after ${Thread.currentThread().name}")
            return result.value as T
        }
    }
}

data class Result(val value: Any?);
data class RPC(val continuation: Continuation<Result>, val block: () -> Any?)

class ServerA : Service() {
    override suspend fun update(mills: Long) {
        super.update(mills)
        delay(10000)
        requests.poll()?.let {
            it.continuation.resume(Result(it.block()))
        }
    }

    companion object {
        suspend fun testMethod(): Int = wrap {
            println("return value ${Thread.currentThread().name}")
            return@wrap 123
        }
    }
}

class ServerB : Service() {
    override suspend fun update(mills: Long) {
        super.update(mills)
        if (mills / 1000 == 2L) {
            println("start getValue ${Thread.currentThread().name}")
            val value = ServerA.testMethod()
            println("got value = $value ${Thread.currentThread().name}")
        }
    }
}