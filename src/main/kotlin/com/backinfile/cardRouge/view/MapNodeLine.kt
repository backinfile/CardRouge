package com.backinfile.cardRouge.view

import com.backinfile.cardRouge.viewGroups.MapViewGroup.Companion.icon_border_size
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Line

class MapNodeLine(lastIcon: MapNodeIcon, curIcon: MapNodeIcon) : Group() {
    val fromRoomId: Int
    val toRoomId: Int
    private val line: Line

    init {
        fromRoomId = lastIcon.roomId
        toRoomId = curIcon.roomId
        line = Line()
        line.stroke = Color.BLACK
        line.startX = lastIcon.translateX + icon_border_size / 2f + 2
        line.startY = lastIcon.translateY
        line.endX = curIcon.translateX - icon_border_size / 2f - 2
        line.endY = curIcon.translateY

        this.children.add(line)
    }

    fun setVisited() {
        line.strokeWidth = 3.0
        line.stroke = Color.WHITE
    }
}