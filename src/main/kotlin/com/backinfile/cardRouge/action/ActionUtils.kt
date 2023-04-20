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
    val lock = getAsyncLock()
    this.timerQueue.apply(delay = duration.toSeconds()) {
        lock.close()
        it.resume(Unit)
    }
}

/**
 * 等待条件完成(基于游戏update)
 */
suspend fun Board.waitCondition(condition: () -> Boolean) = suspendCoroutine {
    val lock = getAsyncLock()
    this.addUpdater(object : Board.Updatable() {
        override fun onUpdate(tpf: Double) {
            if (condition.invoke()) {
                destroy()
                lock.close()
                it.resume(Unit)
            }
        }
    })
}


/**
 * 等待条件完成(基于ObservableValue)
 */
suspend fun <T> Board.waitCondition(observableValue: ObservableValue<T>, condition: (T) -> Boolean) = suspendCoroutine {
    val lock = getAsyncLock()
    observableValue.addListener(object : ChangeListener<T> {
        override fun changed(observable: ObservableValue<out T>, oldValue: T, newValue: T) {
            if (condition.invoke(newValue)) {
                observable.removeListener(this)
                lock.close()
                it.resume(Unit)
            }
        }
    })
}