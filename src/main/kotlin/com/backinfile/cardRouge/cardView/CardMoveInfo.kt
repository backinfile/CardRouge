package com.backinfile.cardRouge.cardView

import com.almasb.fxgl.core.math.Vec2
import com.backinfile.support.fxgl.map
import com.backinfile.support.kotlin.d
import javafx.scene.Group

class CardMoveInfo {
    val position = MovingFieldVec2(Vec2(0.0, 0.0))
    val rotation = MovingFieldDouble(0.0)
    val scale = MovingFieldDouble(1.0)

    fun bindBy(group: Group) {
        group.translateXProperty().bind(position.observable.map { it.x.d })
        group.translateYProperty().bind(position.observable.map { it.y.d })
        group.rotateProperty().bind(rotation.observable)
        group.scaleXProperty().bind(scale.observable)
        group.scaleYProperty().bind(scale.observable)
    }

    fun update(delta: Double) {
        position.update(delta)
        rotation.update(delta)
        scale.update(delta)
    }


}