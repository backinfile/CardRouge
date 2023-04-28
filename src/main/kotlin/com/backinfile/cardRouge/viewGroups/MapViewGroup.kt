package com.backinfile.cardRouge.viewGroups

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.getGameScene
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.room.RoomSimple
import com.backinfile.cardRouge.view.MapNodeIcon
import com.backinfile.cardRouge.view.MapNodeLine
import com.backinfile.support.fxgl.FXGLUtils
import com.backinfile.support.fxgl.clamp
import com.backinfile.support.fxgl.map
import com.backinfile.support.kotlin.Action1
import com.backinfile.support.kotlin.Random
import com.backinfile.support.kotlin.d
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

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
        exitButton = FXGLUtils.btnBottomCenter(Res.TEXT_CLOSE) { hide() }
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
        val onClick = { roomId: Int ->
            for (id in selectRoomIds) {
                val icon = mapNodeIconMap[id]!!
                icon.setPulse(false)
                icon.setVisited(false)
                icon.onMouseClicked = null
            }
            selected.invoke(roomId)
        }
        for (roomId in selectRoomIds) {
            val mapNodeIcon = mapNodeIconMap[roomId]!!
            mapNodeIcon.setPulse(true)
            mapNodeIcon.onMouseClicked = EventHandler { onClick.invoke(roomId) }
        }
    }

    fun hide() {
        if (!isShow) {
            return
        }
        isShow = false
        FXGL.getGameScene().removeUINode(this)
    }

    fun createMap(roomSimpleMap: Map<Int, RoomSimple>) {
        mapNodeIconMap.clear()
        mapNodeLines.clear()
        mapNodeGroup.children.clear()
        visitedRooms.clear()
        assert(roomSimpleMap.isNotEmpty())
        val startLevel = roomSimpleMap.keys.stream().mapToInt { id: Int -> id / 10 }.min().asInt
        val endLevel = roomSimpleMap.keys.stream().mapToInt { id: Int -> id / 10 }.max().asInt
        val backgroundLeft = 0.0
        val backgroundRight = endLevel * gepX
        val background = Rectangle(endLevel * gepX, totalY)
        background.fill = Color.DARKSLATEGREY
        background.stroke = Color.BLACK
        background.strokeWidth = 3.0
        background.cursor = Cursor.OPEN_HAND
        background.onMousePressed = EventHandler {
            val startX: Double = mapNodeGroup.translateX - FXGL.getInput().mouseXUI
            mapNodeGroup.translateXProperty().bind(FXGL.getInput().mouseXUIProperty().map {
                clamp(
                    FXGL.getInput().mouseXUI + startX,
                    -backgroundRight + Config.SCREEN_WIDTH,
                    -backgroundLeft
                )
            })
        }
        background.onMouseReleased = EventHandler { mapNodeGroup.translateXProperty().unbind() }
        mapNodeGroup.children.add(background)
        val lastRoomIds: MutableList<Int> = ArrayList()
        for (level in startLevel..endLevel) {
            val roomIds = roomSimpleMap.keys.filter { id: Int -> id / 10 == level }.sorted().toList()
            val iconCount = roomIds.size
            for (index in 0 until iconCount) {
                val roomId = roomIds[index]
                val roomSimple = roomSimpleMap[roomId]!!
                val nodeIcon = MapNodeIcon(roomId, roomSimple)
                val rnd = 20
                nodeIcon.translateX = Random.instance.next(-rnd, rnd + 1) + (level - 0.5f) * gepX
                nodeIcon.translateY = Random.instance.next(-rnd, rnd + 1) + totalY / (iconCount + 1) * (index + 1)
                mapNodeIconMap[roomId] = nodeIcon
                mapNodeGroup.children.add(nodeIcon)
            }
            if (lastRoomIds.isEmpty()) {
                // no line
            } else if (roomIds.size == 1 || lastRoomIds.size == 1) {
                for (lastRoomId in lastRoomIds) {
                    for (curRoomId in roomIds) {
                        val lastIcon = mapNodeIconMap[lastRoomId]!!
                        val curIcon = mapNodeIconMap[curRoomId]!!
                        val line = MapNodeLine(lastIcon, curIcon)
                        mapNodeLines.add(line)
                        mapNodeGroup.children.add(line)
                    }
                }
            } else {
                for (curRoomId in roomIds) {
                    val lastIcon = mapNodeIconMap[(level - 1) * 10 + curRoomId % 10]!!
                    val curIcon = mapNodeIconMap[curRoomId]!!
                    val line = MapNodeLine(lastIcon, curIcon)
                    mapNodeLines.add(line)
                    mapNodeGroup.children.add(line)
                }
            }
            lastRoomIds.clear()
            lastRoomIds.addAll(roomIds)
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
        const val icon_size = 50
        private const val gepX = 150.0
        private const val totalY = 500.0
        const val icon_border_size = (icon_size * 1.2f).toDouble()
    }
}
