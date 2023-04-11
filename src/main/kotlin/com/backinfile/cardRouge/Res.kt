package com.backinfile.cardRouge

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.texture.Texture
import com.almasb.fxgl.texture.getDummyImage
import com.backinfile.cardRouge.gen.config.ConfCard
import com.backinfile.cardRouge.gen.config.ConfigManager
import com.backinfile.support.SysException
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

    private const val IMG_DEFAULT_CARD_IMG = "fire_tmp_1.png"
    private const val IMG_DEFAULT_CARD_IMG_BACK = "default_back.jpg"


    val TEXT_BATTLE_START = "战斗开始"
    val TEXT_TURN_END = "回合结束"
    val TEXT_OPPONENT_TURN = "对手回合"
    val TEXT_CONFIRM = "确认"
    val TEXT_CANCEL = "取消"

    fun loadAll() {
        Log.game.info("res load start")

        // 加载配置文件
        ConfigManager.setLogger(ConfigLogger())
        ConfigManager.setConfigFileReader(Res::readConfigJsonFile)
        ConfigManager.loadAll()


        // 加载图片文件
        for (field in Res::class.java.declaredFields) {
            if (Modifier.isStatic(field.modifiers) && field.name.startsWith("IMG_") && field.type == String::class.java) {
                preloadTexture(field[null] as String)
            }
        }
        for (conf in ConfCard.getAll()) {
            preloadTexture(conf.image)
            preloadTexture(conf.backImage)
        }
        Log.game.info("res load finish")
    }

    fun loadImage(path: String): Image {
        return FXGL.getAssetLoader().loadImage(path)
    }

    fun loadTexture(path: String): Texture {
        return FXGL.getAssetLoader().loadTexture(path)
    }

    fun loadCardImage(confCard: ConfCard, front: Boolean): Image {
        return if (front) {
            if (!confCard.image.isEmpty()) {
                val image = FXGL.getAssetLoader().loadImage(confCard.image)
                if (image !== getDummyImage()) {
                    return image
                }
            }
            loadImage(IMG_DEFAULT_CARD_IMG) // 找不到卡图，返回一个默认的
        } else {
            if (!confCard.backImage.isEmpty()) {
                val image = FXGL.getAssetLoader().loadImage(confCard.backImage)
                if (image !== getDummyImage()) {
                    return image
                }
            }
            loadImage(IMG_DEFAULT_CARD_IMG_BACK) // 找不到卡图，返回一个默认的
        }
    }

    fun loadCardTexture(confCard: ConfCard, front: Boolean): Texture {
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


    private class ConfigLogger : ConfigManager.Logger() {
        override fun info(message: String) {
            Log.config.info(message)
        }

        override fun error(message: String) {
            Log.config.error(message)
        }

        override fun error(message: String, e: Exception) {
            Log.config.error(message, e)
        }

        override fun logConfigMissing(configClassName: String, id: Any) {
            Log.config.error("missing config id:$id of $configClassName", SysException())
        }
    }
}
