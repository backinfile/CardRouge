package com.backinfile.cardRouge.cardView

import com.almasb.fxgl.core.math.FXGLMath
import com.almasb.fxgl.core.math.Vec2
import com.backinfile.cardRouge.Log
import com.backinfile.support.kotlin.d
import com.backinfile.support.kotlin.f
import javafx.beans.property.SimpleObjectProperty
import javafx.util.Duration
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sqrt

abstract class MovingField<T>(initValue: T) {
    protected val wrappedValue = TriggerProperty(initValue)

    protected class TriggerProperty<T>(initValue: T) : SimpleObjectProperty<T>(initValue) {
        fun fireChanged() {
            invalidated()
            fireValueChangedEvent()
        }
    }

    val observable get() = wrappedValue as SimpleObjectProperty<T>

    val value: T get() = wrappedValue.get()

    protected abstract var from: T
    protected abstract var to: T

    var moving = false
        protected set

    abstract fun setTarget(target: T, duration: Duration)

    abstract fun update(delta: Double)
}

class MovingFieldDouble(initValue: Double) : MovingField<Double>(initValue) {
    override var from = initValue
    override var to = initValue
    private var speed = 0.0

    override fun setTarget(target: Double, duration: Duration) {
        if (duration.toMillis() <= 0 || abs(target - wrappedValue.get()) < 0.001) {
            wrappedValue.set(target)
            moving = false
            return
        }

        moving = true
        from = wrappedValue.get()
        to = target
        speed = abs(to - from) / duration.toSeconds()
    }

    override fun update(delta: Double) {
        if (!moving) return

        val sign = if (from <= to) 1 else -1

        val updateValue = wrappedValue.get() + delta * speed * sign
        if (sign * updateValue >= sign * to) {
            moving = false
            wrappedValue.set(to)
        } else {
            wrappedValue.set(updateValue)
        }
    }
}

class MovingFieldVec2(initValue: Vec2) : MovingField<Vec2>(initValue) {
    override var from = Vec2(initValue)
    override var to = Vec2(initValue)

    private var speedX = 0.0
    private var speedY = 0.0


    override fun setTarget(target: Vec2, duration: Duration) {
        if (duration.toMillis() <= 0) {
            wrappedValue.get().set(target)
            wrappedValue.fireChanged()
            moving = false
            return
        }
        from.set(wrappedValue.get())
        to.set(target)
        val distance = to.distance(from)
        if (distance < 0.001) {
            wrappedValue.get().set(target)
            wrappedValue.fireChanged()
            moving = false
            return
        }
        val speed = distance / duration.toSeconds()
        moving = true
        val dx = to.x - from.x
        val dy = to.y - from.y
        speedX = dx / distance * speed
        speedY = dy / distance * speed
    }

    override fun update(delta: Double) {
        if (!moving) return

        val signX = if (to.x >= from.x) 1 else -1
        val signY = if (to.y >= from.y) 1 else -1

        val updateX = wrappedValue.get().x + speedX * delta
        val updateY = wrappedValue.get().y + speedY * delta

        var over = true

        if (updateX * signX >= to.x * signX) {
            wrappedValue.get().x = to.x
        } else {
            over = false
            wrappedValue.get().x = updateX.f
        }

        if (updateY * signY >= to.y * signY) {
            wrappedValue.get().y = to.y
        } else {
            over = false
            wrappedValue.get().y = updateY.f
        }

        wrappedValue.fireChanged()
        if (over) {
            moving = false
        }
    }

}