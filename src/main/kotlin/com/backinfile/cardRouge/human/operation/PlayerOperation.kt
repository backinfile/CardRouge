package com.backinfile.cardRouge.human.operation

import com.almasb.fxgl.core.Updatable
import com.backinfile.cardRouge.Game
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class PlayerOperation<R>(protected val continuation: Continuation<R>) {
    fun resume(r: R) {
        continuation.resume(r)
    }

    abstract suspend fun startAsync()

    suspend fun waitCondition(condition: () -> Boolean) = suspendCoroutine {
        Game.getScene().addListener(WaitCondition(condition, it))
    }

    private class WaitCondition(val condition: () -> Boolean, val continuation: Continuation<Unit>) : Updatable {
        override fun onUpdate(tpf: Double) {
            if (condition.invoke()) {
                Game.getScene().removeListener(this)
                continuation.resume(Unit)
            }
        }

    }

}