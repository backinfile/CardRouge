package com.backinfile.support.fxgl

import javafx.beans.binding.ObjectBinding
import javafx.beans.value.ObservableDoubleValue
import javafx.beans.value.ObservableIntegerValue
import javafx.beans.value.ObservableValue
import javafx.scene.layout.Region
import javafx.scene.paint.Color

class FxUtils {
}


fun opacityColor(opacity: Double) = Color(0.0, 0.0, 0.0, opacity)


fun Region.setSize(width: Double, height: Double) {
    this.setPrefSize(width, height)
    this.setMaxSize(width, height)
    this.setMinSize(width, height)
}

fun Region.setByCenter(x: Double, y: Double, width: Double, height: Double) {
    setSize(width, height)
    translateX = x - width / 2.0
    translateY = y - height / 2.0
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