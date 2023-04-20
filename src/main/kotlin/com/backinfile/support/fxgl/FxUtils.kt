package com.backinfile.support.fxgl

import javafx.beans.binding.ObjectBinding
import javafx.beans.value.ObservableDoubleValue
import javafx.beans.value.ObservableIntegerValue
import javafx.beans.value.ObservableValue
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.util.Duration

class FxUtils {
}

fun opacity(opacity: Double) = Color(0.0, 0.0, 0.0, opacity)

fun Region.setSize(width: Double, height: Double) {
    this.setPrefSize(width, height)
    this.setMaxSize(width, height)
    this.setMinSize(width, height)
}



fun <R> ObservableDoubleValue.doubleMap(block: (Double) -> R): ObservableValue<R> {
    val observable = this
    return object : ObjectBinding<R>() {
        init {
            bind(observable)
        }

        override fun computeValue() = block(observable.get())
        override fun dispose() = unbind(observable)
    }
}

fun <R> ObservableIntegerValue.intMap(block: (Int) -> R): ObservableValue<R> {
    val observable = this
    return object : ObjectBinding<R>() {
        init {
            bind(observable)
        }

        override fun computeValue() = block(observable.get())
        override fun dispose() = unbind(observable)
    }
}