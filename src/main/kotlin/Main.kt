import kotlinx.coroutines.*
import kotlinx.coroutines.future.asCompletableFuture
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")


    runAsync {
        println("select before")
        val card = selectCard();
        println("select after")
        println(card)
    }
    updateCheck()
}

class Card
class HumanOper(val continuation: Continuation<Card>)

private val queue = ArrayDeque<HumanOper>()

suspend fun selectCard() = suspendCoroutine {
    println("in select")
    queue.add(HumanOper(it))
}

@OptIn(DelicateCoroutinesApi::class)
fun runAsync(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.async(PureDispatcher) { block() }.asCompletableFuture()
}

fun updateCheck() {
    println("check start------------")
    while (queue.isNotEmpty()) {
        val humanOper = queue.removeFirst()
        humanOper.continuation.resume(Card())
    }
    println("check end------------")
}