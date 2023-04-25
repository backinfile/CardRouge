package com.backinfile.support.fxgl

import com.almasb.fxgl.core.math.Vec2
import com.backinfile.support.kotlin.d
import javafx.beans.binding.ObjectBinding
import javafx.beans.value.ObservableDoubleValue
import javafx.beans.value.ObservableIntegerValue
import javafx.beans.value.ObservableValue
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.layout.Region
import javafx.scene.paint.Color

class FxUtils {
}


fun opacity(opacity: Double) = Color(0.0, 0.0, 0.0, opacity)


fun Region.setSize(width: Double, height: Double) {
    this.setPrefSize(width, height)
    this.setMaxSize(width, height)
    this.setMinSize(width, height)
}

fun Group.setSize(width: Double, height: Double) {
    this.prefWidth(width)
    this.prefHeight(height)
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