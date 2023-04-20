package com.backinfile.support.kotlin

import com.backinfile.support.func.Function0

class Timer(private val interval: Double = 0.0, delay: Double, private val getTimeFunc: Function0<Double>) {
    private var timeout: Double = this.getTimeFunc.invoke() + delay

    val isRunning: Boolean
        get() = timeout >= 0

    val isPeriod: Boolean
        get() {
            if (timeout < 0) {
                return false
            }
            val time = getTimeFunc.invoke()
            if (time >= timeout) {
                if (interval > 0) {
                    timeout += interval
                } else {
                    timeout = -1.0
                }
                return true
            }
            return false
        }
}
