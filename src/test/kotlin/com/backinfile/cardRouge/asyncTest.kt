package com.backinfile.cardRouge

import com.backinfile.support.async.runAsync
import org.junit.Test
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AsyncTest {

    @Test
    fun asyncTest(): Unit {
        runAsync {
            println("select before")
            val card = selectCard();
            println("select after")
            println(card)
        }
        updateCheck()
    }

    private class Card
    private class HumanOper(val continuation: Continuation<Card>)

    private val queue = ArrayDeque<HumanOper>()

    private suspend fun selectCard() = suspendCoroutine {
        println("in select")
        queue.add(HumanOper(it))
    }


    private fun updateCheck() {
        println("check start------------")
        while (queue.isNotEmpty()) {
            val humanOper = queue.removeFirst()
            humanOper.continuation.resume(Card())
        }
        println("check end------------")
    }
}
