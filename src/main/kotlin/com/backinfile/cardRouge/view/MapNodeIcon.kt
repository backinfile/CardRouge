package com.backinfile.cardRouge.view

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.room.RoomSimple
import com.backinfile.cardRouge.viewGroups.MapViewGroup
import com.backinfile.support.Time
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import kotlin.math.abs

class MapNodeIcon(val roomId: Int, val roomSimple: RoomSimple) : Group() {
    private var icon: ImageView
    private var border: Circle
    private var tip: Label
    private var pulse = false

    init {
        this.cursor = Cursor.HAND

        border = Circle(0.0, 0.0, MapViewGroup.icon_border_size / 2f)
        with(border) {
            fill = Color.TRANSPARENT
            stroke = Color.TRANSPARENT
        }

        icon = ImageView(Res.loadImage(roomSimple.roomType.iconPath))
        with(icon) {
            fitWidth = MapViewGroup.icon_size.toDouble()
            fitHeight = MapViewGroup.icon_size.toDouble()
            translateX = -MapViewGroup.icon_size / 2.0
            translateY = -MapViewGroup.icon_size / 2.0
        }


        tip = Label(roomSimple.roomType.text)
        with(tip) {
            translateXProperty().bind(widthProperty().multiply(-0.5f))
            translateY = -MapViewGroup.icon_border_size / 2
            textFill = Color.WHITE
            isVisible = false
        }

        children.addAll(border, icon, tip)
        this.onMouseEntered = EventHandler {
            border.fill = Color(1.0, 1.0, 1.0, 0.1)
            tip.isVisible = true
        }
        this.onMouseExited = EventHandler {
            border.fill = Color.TRANSPARENT
            tip.isVisible = false
        }
        setVisited(false)
    }

    fun setVisited(visited: Boolean) {
        setPulse(false)
        if (visited) {
            border.stroke = Color(1.0, 1.0, 1.0, 0.9)
            border.strokeWidth = 2.5
        } else {
            border.stroke = Color.TRANSPARENT
            border.strokeWidth = 1.5
        }
        //            Log.debug.info("room {} set visited {}", roomId, visited);
    }

    fun setPulse(pulse: Boolean) {
        this.pulse = pulse
        border.opacity = 1.0
        if (pulse) {
            border.stroke = Color(1.0, 1.0, 1.0, 0.5)
        } else {
            border.stroke = Color.TRANSPARENT
        }
        //            Log.debug.info("room {} set pulse {}", roomId, pulse);
    }

    fun update() {
        if (pulse) {
            val curMillis = Time.getCurMillis()
            val pulseTime = 800
            val opacity = abs(curMillis % pulseTime - pulseTime / 2.0) / (pulseTime / 2.0)
            border.opacity = minOf(0.9, opacity) + 0.1f
        }
    }
}