package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.board.Board
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.util.Duration
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ActionUtils {
}


suspend fun Board.waitTime(duration: Duration) = suspendCoroutine {
    this.timerQueue.apply(delay = duration.toSeconds()) { it.resume(Unit) }
}

/**
 * 等待条件完成(基于游戏update)
 */
suspend fun Board.waitCondition(condition: () -> Boolean) = suspendCoroutine {
    this.addUpdater(object : Board.Updatable() {
        override fun onUpdate(tpf: Double) {
            if (condition.invoke()) {
                destory()
                it.resume(Unit)
            }
        }
    })
}


/**
 * 等待条件完成(基于ObservableValue)
 */
suspend fun <T> ObservableValue<T>.waitCondition(condition: () -> Boolean) = suspendCoroutine {
    this.addListener(object : ChangeListener<T> {
        override fun changed(observable: ObservableValue<out T>, oldValue: T, newValue: T) {
            if (condition.invoke()) {
                observable.removeListener(this)
                it.resume(Unit)
            }
        }
    })
}