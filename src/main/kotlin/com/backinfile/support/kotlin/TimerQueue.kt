package com.backinfile.support.kotlin

import com.backinfile.cardRouge.Log
import java.util.concurrent.ConcurrentHashMap

class TimerQueue(private var getTimeFunc: Function0<Double>) {
    private val timers: MutableMap<Long, TimeEvent> = ConcurrentHashMap()
    private var idMax: Long = 1

    private class TimeEvent(var timer: Timer, var action: Runnable)

    fun apply(interval: Double = 0.0, delay: Double, action: Runnable): Long {
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
                    timeEvent.action.run()
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
