package com.backinfile.support.kotlin

import com.backinfile.cardRouge.Log
import com.backinfile.support.func.Action0
import com.backinfile.support.func.Function0
import java.util.concurrent.ConcurrentHashMap

class TimerQueue(private var getTimeFunc: Function0<Double>) {
    private val timers: MutableMap<Long, TimeEvent> = ConcurrentHashMap()
    private var idMax: Long = 1

    private class TimeEvent(var timer: Timer, var action: Action0)

    fun apply(interval: Double = 0.0, delay: Double, action: Action0): Long {
        val id = idMax++
        timers[id] = TimeEvent(Timer(interval, delay, getTimeFunc), action)
        return id
    }

    fun remove(timerId: Long) {
        timers.remove(timerId)
    }

    fun clear() {
        timers.clear()
    }

    fun update() {
        for ((id, timeEvent) in timers) {
            if (timeEvent.timer.isPeriod) {
                try {
                    timeEvent.action.invoke()
                } catch (e: Exception) {
                    Log.game.error("error in TimerQueue", e)
                }
                if (!timeEvent.timer.isRunning) {
                    timers.remove(id)
                }
            }
        }
    }
}
