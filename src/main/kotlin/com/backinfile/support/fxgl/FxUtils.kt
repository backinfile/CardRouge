package com.backinfile.support.fxgl

import com.almasb.fxgl.core.math.Vec2
import javafx.beans.binding.DoubleBinding
import javafx.beans.binding.FloatBinding
import javafx.beans.binding.ObjectBinding
import javafx.beans.value.ObservableObjectValue
import javafx.beans.value.ObservableValue

class FxUtils {
}


fun <T> ObservableObjectValue<T>.wrapper(block: (T) -> Double): DoubleBinding {
    val observable = this@wrapper
    return object : DoubleBinding() {
        init {
            bind(observable)
        }

        override fun computeValue() = block(observable.get())
        override fun dispose() = unbind(observable)

    }
}

fun <T, R> ObservableObjectValue<T>.wrapper(block: (T) -> R): ObservableValue<R> {
    val observable = this@wrapper
    return object : ObjectBinding<R>() {
        init {
            bind(observable)
        }

        override fun computeValue() = block(observable.get())
        override fun dispose() = unbind(observable)

    }
}
