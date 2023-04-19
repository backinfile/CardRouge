package com.backinfile.cardRouge.action

import com.almasb.fxgl.core.Updatable
import com.backinfile.cardRouge.Game
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.util.Duration
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ActionUtils {
}


suspend fun waitTime(duration: Duration) = suspendCoroutine {
    Game.getScene().timer.runOnceAfter({ it.resume(Unit) }, duration)
}

/**
 * 等待条件完成(基于游戏update)
 */
suspend fun waitCondition(condition: () -> Boolean) = suspendCoroutine {
    Game.getScene().addListener(object : Updatable {
        override fun onUpdate(tpf: Double) {
            if (condition.invoke()) {
                Game.getScene().removeListener(this)
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