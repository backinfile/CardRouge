package com.backinfile.cardRouge.viewGroups

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.getGameScene
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.room.RoomSimple
import com.backinfile.support.MathUtils
import com.backinfile.support.Random
import com.backinfile.support.Time
import com.backinfile.support.func.Action1
import com.backinfile.support.fxgl.FXGLUtils
import com.backinfile.support.kotlin.d
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import java.util.stream.Collectors

class MapViewGroup internal constructor() : Group() {
    private val mapNodeIconMap: MutableMap<Int, MapNodeIcon> = HashMap()
    private val mapNodeLines: MutableList<MapNodeLine> = ArrayList()
    private val mapNodeGroup = Group()
    private val visitedRooms: MutableSet<Int> = HashSet()
    private val exitButton: Node
    private var isShow = false

    init {
        val mask = Rectangle(Config.SCREEN_WIDTH.d, Config.SCREEN_HEIGHT.d)
        mask.fill = Config.BACKGROUND_COLOR
        children.add(mask)
        mapNodeGroup.translateX = 0.0
        mapNodeGroup.translateY = (Config.SCREEN_HEIGHT - totalY) / 2f
        children.add(mapNodeGroup)
        exitButton = FXGLUtils.btnBottomCenter("关闭") { hide() }
        children.add(exitButton)
    }

    fun show() {
        if (isShow) {
            return
        }
        isShow = true
        getGameScene().addUINode(this)
    }

    fun show(selectRoomIds: List<Int>, closeable: Boolean, selected: Action1<Int>) {
        show()
        exitButton.isVisible = closeable
        val onClick: Action1<Int> = Action1<Int> { roomId: Int? ->
            for (id in selectRoomIds) {
                val icon = mapNodeIconMap[id]
                icon!!.setPulse(false)
                icon.setVisited(false)
                icon.onMouseClicked = null
            }
            selected.invoke(roomId)
        }
        for (roomId in selectRoomIds) {
            val mapNodeIcon = mapNodeIconMap[roomId]
            mapNodeIcon!!.setPulse(true)
            mapNodeIcon.onMouseClicked = EventHandler { e: MouseEvent? -> onClick.invoke(roomId) }
        }
    }

    fun hide() {
        if (!isShow) {
            return
        }
        isShow = false
        FXGL.getGameScene().removeUINode(this)
    }

    fun createMap(roomSimpleMap: Map<Int, RoomSimple?>) {
        mapNodeIconMap.clear()
        mapNodeLines.clear()
        mapNodeGroup.children.clear()
        visitedRooms.clear()
        assert(!roomSimpleMap.isEmpty())
        val startLevel = roomSimpleMap.keys.stream().mapToInt { id: Int -> id / 10 }.min().asInt
        val endLevel = roomSimpleMap.keys.stream().mapToInt { id: Int -> id / 10 }.max().asInt
        val backgroundLeft = 0.0
        val backgroundRight = endLevel * gepX
        val background = Rectangle(endLevel * gepX, totalY)
        background.fill = Color.DARKSLATEGREY
        background.stroke = Color.BLACK
        background.strokeWidth = 3.0
        background.cursor = Cursor.OPEN_HAND
//        background.onMousePressed = EventHandler<MouseEvent> { event: MouseEvent? ->
//            val startX: Double = mapNodeGroup.translateX - FXGL.getInput().mouseXUI
//            mapNodeGroup.translateXProperty().bind(object : DoubleWrapper(FXGL.getInput().mouseXUIProperty()) {
//                protected fun computeValue(): Double {
//                    return@setOnMousePressed MathUtils.clamp(
//                        FXGL.getInput().mouseXUI + startX,
//                        -backgroundRight + Config.SCREEN_WIDTH,
//                        -backgroundLeft
//                    )
//                }
//            })
//        }
        background.onMouseReleased = EventHandler { event: MouseEvent? -> mapNodeGroup.translateXProperty().unbind() }
        mapNodeGroup.children.add(background)
        val lastRoomIds: MutableList<Int> = ArrayList()
        for (level in startLevel..endLevel) {
            val roomIds =
                roomSimpleMap.keys.stream().filter { id: Int -> id / 10 == level }.sorted().collect(Collectors.toList())
            val iconCount = roomIds.size
            for (index in 0 until iconCount) {
                val roomId = roomIds[index]
                val roomSimple: RoomSimple? = roomSimpleMap[roomId]
                val nodeIcon = MapNodeIcon(roomId, roomSimple)
                val rnd = 20
                nodeIcon.translateX = Random.getInstance().next(-rnd, rnd + 1) + (level - 0.5f) * gepX
                nodeIcon.translateY = Random.getInstance().next(-rnd, rnd + 1) + totalY / (iconCount + 1) * (index + 1)
                mapNodeIconMap[roomId] = nodeIcon
                mapNodeGroup.children.add(nodeIcon)
            }
            if (lastRoomIds.isEmpty()) {
                // no line
            } else if (roomIds.size == 1 || lastRoomIds.size == 1) {
                for (lastRoomId in lastRoomIds) {
                    for (curRoomId in roomIds) {
                        val lastIcon = mapNodeIconMap[lastRoomId]
                        val curIcon = mapNodeIconMap[curRoomId]
                        val line = MapNodeLine(lastIcon, curIcon)
                        mapNodeLines.add(line)
                        mapNodeGroup.children.add(line)
                    }
                }
            } else {
                for (curRoomId in roomIds) {
                    val lastIcon = mapNodeIconMap[(level - 1) * 10 + curRoomId % 10]
                    val curIcon = mapNodeIconMap[curRoomId]
                    val line = MapNodeLine(lastIcon, curIcon)
                    mapNodeLines.add(line)
                    mapNodeGroup.children.add(line)
                }
            }
            lastRoomIds.clear()
            lastRoomIds.addAll(roomIds)
        }
    }

    private class MapNodeIcon(val roomId: Int, roomSimple: RoomSimple?) : Group() {
        var icon: ImageView
        var border: Circle
        var tip: Label
        val roomSimple: RoomSimple?
        private var pulse = false

        init {
            this.roomSimple = roomSimple
            this.cursor = Cursor.HAND
            border = Circle(0.0, 0.0, icon_border_size / 2f)
            border.fill = Color.TRANSPARENT
            border.stroke = Color.TRANSPARENT
            icon = ImageView(Res.loadImage(roomSimple?.roomType.iconPath))
            icon.fitWidth = icon_size.toDouble()
            icon.fitHeight = icon_size.toDouble()
            icon.translateX = (-icon_size / 2f).toDouble()
            icon.translateY = (-icon_size / 2f).toDouble()
            tip = Label(roomSimple.roomType.text)
            tip.translateXProperty().bind(tip.widthProperty().multiply(-0.5f))
            tip.translateY = -icon_border_size / 2
            tip.textFill = Color.WHITE
            tip.isVisible = false
            children.addAll(border, icon, tip)
            this.onMouseEntered = EventHandler { e: MouseEvent? ->
                border.fill = Color(1.0, 1.0, 1.0, 0.1)
                tip.isVisible = true
            }
            this.onMouseExited = EventHandler { e: MouseEvent? ->
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
                var opacity = Math.abs(curMillis % pulseTime - pulseTime / 2) / (pulseTime / 2f)
                opacity = Math.min(0.9f, opacity) + 0.1f
                border.opacity = opacity.toDouble()
            }
        }
    }

    private class MapNodeLine(lastIcon: MapNodeIcon?, curIcon: MapNodeIcon?) : Group() {
        val fromRoomId: Int
        val toRoomId: Int
        private val line: Line

        init {
            fromRoomId = lastIcon!!.roomId
            toRoomId = curIcon!!.roomId
            line = Line()
            line.stroke = Color.BLACK
            line.startX = lastIcon.translateX + icon_border_size / 2f + 2
            line.startY = lastIcon.translateY
            line.endX = curIcon.translateX - icon_border_size / 2f - 2
            line.endY = curIcon.translateY
            children.add(line)
        }

        fun setVisited() {
            line.strokeWidth = 3.0
            line.stroke = Color.WHITE
        }
    }

    fun update() {
        if (!isShow) {
            return
        }
        for (icon in mapNodeIconMap.values) {
            icon.update()
        }
    }

    fun setRoomVisited(roomId: Int, visited: Boolean) {
        mapNodeIconMap[roomId]!!.setVisited(visited)
        if (visited) {
            visitedRooms.add(roomId)
        } else {
            visitedRooms.remove(roomId)
        }
        if (visited) {
            for (line in mapNodeLines) {
                if (line.toRoomId == roomId && visitedRooms.contains(line.fromRoomId)) {
                    line.setVisited()
                    break
                }
            }
        }
    }

    companion object {
        private const val icon_size = 50
        private const val gepX = 150.0
        private const val totalY = 500.0
        private const val icon_border_size = (icon_size * 1.2f).toDouble()
    }
}
