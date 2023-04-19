package com.backinfile.cardRouge.action

import com.almasb.fxgl.core.Updatable
import com.backinfile.cardRouge.Game
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.support.async.runAsync
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

typealias GameAction = suspend ActionContext.() -> Unit
typealias GameActionRun = suspend () -> Unit

object Actions {
    suspend fun ActionContext.drawCard(number: Number) {
        shuffleDrawPile()

    }

    suspend fun ActionContext.shuffleDrawPile() {

    }

    suspend fun ActionContext.selectCardFrom(cards: List<Card>, cnt: Int = 1, optional: Boolean = false): List<Card> = suspendCoroutine { continuation ->
        board.inAsyncEvent = true

        // 异步执行
        runAsync {
            val selected = ArrayList<Card>()
            for (card in cards) {
                val cardView = CardViewManager.getOrCreate(card)
                cardView.modInteract.disableAll()
                cardView.modInteract.enableClick {
                    if (it.card in selected) selected.remove(it.card) else selected.add(it.card)
                }
                cardView.modInteract.setDark(false)
            }

            waitCondition { selected.size == cnt } // 等待满足条件

            board.inAsyncEvent = false
            continuation.resume(selected) // 继续执行当前行动
        }
    }


    suspend fun <T> ObservableValue<T>.waitCondition(condition: () -> Boolean) = suspendCoroutine<Unit> {
        this.addListener(WaitConditionListener(condition, it))
    }

    private class WaitConditionListener<T>(val condition: () -> Boolean,
                                           val continuation: Continuation<Unit>) : ChangeListener<T> {
        override fun changed(observable: ObservableValue<out T>, oldValue: T, newValue: T) {
            if (condition.invoke()) {
                observable.removeListener(this)
                continuation.resume(Unit)
            }
        }
    }

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