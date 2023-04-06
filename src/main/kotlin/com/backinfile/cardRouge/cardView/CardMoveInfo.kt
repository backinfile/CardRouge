package com.backinfile.cardRouge.cardView

import com.almasb.fxgl.core.math.Vec2
import com.backinfile.support.kotlin.d
import javafx.beans.binding.DoubleBinding
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.scene.control.Label

class CardMoveInfo {
    val position = SimpleObjectProperty<Vec2>()
    val rotation = MovingField(0.0)

    fun applyTo(label: Label) {
        label.translateXProperty().bind(object : ValueWrapper<Vec2>(position) {
            override fun computeValue(): Double {
                return observable.value.x.d
            }
        })
        label.translateXProperty().bind(object : ValueWrapper<Vec2>(position) {
            override fun computeValue(): Double {
                return observable.value.y.d
            }
        })
        label.rotateProperty().bind(rotation.curValue)
    }

    private abstract class ValueWrapper<T>(protected val observable: ObservableValue<T>) : DoubleBinding() {
        init {
            bind(observable)
        }

        override fun dispose() {
            unbind(observable)
        }
    }
}