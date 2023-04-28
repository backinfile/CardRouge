package com.backinfile.cardRouge

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.texture.Texture
import com.almasb.fxgl.texture.getDummyImage
import com.backinfile.cardRouge.card.CardConfig
import javafx.scene.Cursor
import javafx.scene.ImageCursor
import javafx.scene.image.Image
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.reflect.Modifier
import java.nio.charset.StandardCharsets

object Res {
    // IMG_开头的变量会自动加载
    const val IMG_MANA = "mana_2.png"
    const val IMG_EDGE1 = "edge_1.png"
    const val IMG_EDGE2 = "edge_2.png"
    const val IMG_EDGE3 = "edge_3.png"
    const val IMG_EDGE4 = "edge_4.png"


    const val IMG_GLOW_WHITE = "glow_white.png"
    const val IMG_GLOW_BLUE = "glow_blue.png"
    const val IMG_ICON_DRAW_PILE = "draw_pile.png"
    const val IMG_ICON_DISCARD_PILE = "discard_pile.png"
    const val IMG_ICON_POWER_PILE = "power_pile.png"
    const val IMG_SELECTED_MARK = "selected.png"
    const val IMG_NO_SELECT_MARK = "mark_not_select.png"
    const val IMG_DISORDER_MARK = "disorder.png"
    const val IMG_FAST_SUMMON_MARK = "fastSummon.png"
    const val IMG_ICON_SETTING = "icon_setting.png"
    const val IMG_ICON_MAP = "icon_map.png"
    const val IMG_ICON_SLOW = "icon_cost.png"
    const val IMG_ICON_FAST =  "icon_fast.png"
    const val IMG_ROOM_ICON_START = "room/room_start.png"
    const val IMG_ROOM_ICON_BATTLE = "room/room_battle.png"
    const val IMG_ROOM_ICON_BATTLE_ELITE = "room/room_battle_elite.png"
    const val IMG_ROOM_ICON_HEAL = "room/room_heal.png"
    const val IMG_ROOM_ICON_SELECT_CARD = "room/room_select_card.png"
    const val IMG_ROOM_ICON_SELECT_POWER = "room/room_select_power.png"
    const val IMG_ROOM_ICON_COPY = "room/room_copy.png"
    const val IMG_ROOM_ICON_CHANGE = "room/room_change.png"
    const val IMG_ROOM_ICON_SHOP = "room/room_shop.png"
    const val IMG_ROOM_ICON_SNATCH = "room/room_snatch.png"


    const val FONT_FAMILY_DEFAULT = "System Regular"
    const val FONT_SIZE_DEFAULT = 24;

    val Cursor_Magnifier: Cursor = ImageCursor(loadImage("magnifying-glass.png"))

    const val IMG_DEFAULT_CARD_IMG = "fire_tmp_1.png"
    const val IMG_DEFAULT_CARD_IMG_BACK = "default_back.jpg"


    val TEXT_BATTLE_START = "战斗开始"
    val TEXT_TURN_END = "回合结束"
    val TEXT_OPPONENT_TURN = "对手回合"
    val TEXT_CONFIRM = "确认"
    val TEXT_CANCEL = "取消"
    val TEXT_CLOSE = "关闭"

    fun loadAll() {
        println(System.getProperty("user.dir"))
        Log.game.info("res load start")


        // 加载图片文件
        for (field in Res::class.java.declaredFields) {
            if (Modifier.isStatic(field.modifiers) && field.name.startsWith("IMG_") && field.type == String::class.java) {
                preloadTexture(field[null] as String)
            }
        }
        Log.game.info("res load finish")
    }

    fun loadImage(path: String): Image {
        return FXGL.getAssetLoader().loadImage(path)
    }

    fun loadTexture(path: String): Texture {
        return FXGL.getAssetLoader().loadTexture(path)
    }

    fun loadCardImage(confCard: CardConfig, front: Boolean = true): Image {
        return if (front) {
            if (confCard.image.isNotEmpty()) {
                val image = FXGL.getAssetLoader().loadImage(confCard.image)
                if (image !== getDummyImage()) {
                    return image
                }
            }
            loadImage(IMG_DEFAULT_CARD_IMG) // 找不到卡图，返回一个默认的
        } else {
            if (confCard.backImage.isNotEmpty()) {
                val image = FXGL.getAssetLoader().loadImage(confCard.backImage)
                if (image !== getDummyImage()) {
                    return image
                }
            }
            loadImage(IMG_DEFAULT_CARD_IMG_BACK) // 找不到卡图，返回一个默认的
        }
    }

    fun loadCardTexture(confCard: CardConfig, front: Boolean = true): Texture {
        return Texture(loadCardImage(confCard, front))
    }

    fun getLocalString(key: String): String {
        return key
    }


    private fun preloadTexture(path: String) {
        if (path.isNotEmpty()) FXGL.getAssetLoader().loadTexture(path)
    }


    private fun readConfigJsonFile(path: String, fileName: String): String {
        val file = File(path, fileName)
        if (!file.exists()) {
            Log.config.error("file:{} not found", file.absolutePath)
            return ""
        }
        val sb = StringBuilder()
        try {
            BufferedReader(FileReader(file, StandardCharsets.UTF_8)).use { bufferedReader ->
                while (true) {
                    val line = bufferedReader.readLine()
                    if (line != null) {
                        sb.append(line)
                    } else {
                        break
                    }
                }
            }
        } catch (e: Exception) {
            Log.config.error("error in read file:" + file.absolutePath, e)
        }
        return sb.toString()
    }


}
