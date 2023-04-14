package com.backinfile.cardRouge.cardView.mods

import com.almasb.fxgl.core.math.Vec2
import com.backinfile.cardRouge.cardView.*
import com.backinfile.support.fxgl.wrapper
import com.backinfile.support.kotlin.d
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.util.Duration

class ModMove(cardView: CardView) : CardViewBaseMod(cardView) {
    val position = MovingFieldVec2(Vec2(0.0, 0.0))
    val rotation = MovingFieldDouble(0.0)
    val scale = MovingFieldDouble(1.0)

    override fun onCreate() {
        super.onCreate()

        val group = cardView.controlGroup
        group.translateXProperty().bind(position.observable.wrapper { it.x.d })
        group.translateYProperty().bind(position.observable.wrapper { it.y.d })
        group.rotateProperty().bind(rotation.observable)
        group.scaleXProperty().bind(scale.observable)
        group.scaleYProperty().bind(scale.observable)
    }

    override fun update(delta: Double) {
        position.update(delta)
        rotation.update(delta)
        scale.update(delta)
    }

    fun move(
        pos: Point2D? = null,
        rotate: Double? = null,
        scale: Double? = null,
        duration: Duration = Duration.ZERO,
    ): Boolean {
        if (pos != null) {
            this.position.setTarget(Vec2(pos), duration)
        }
        if (rotate != null) {
            this.rotation.setTarget(rotate, duration)
        }
        if (scale != null) {
            this.scale.setTarget(scale, duration)
        }
        return this.position.moving || this.rotation.moving || this.scale.moving
    }
}