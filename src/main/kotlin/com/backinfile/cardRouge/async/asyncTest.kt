package com.backinfile.cardRouge.async

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