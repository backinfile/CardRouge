package com.backinfile.cardRouge

import com.almasb.fxgl.app.ApplicationMode
import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.getInput
import com.almasb.fxgl.dsl.runOnce
import com.almasb.fxgl.localization.Language
import com.backinfile.cardRouge.reflection.GlobalCheck
import com.backinfile.cardRouge.scene.DungeonScene
import com.backinfile.cardRouge.scene.GameSceneFactory
import javafx.util.Duration
import java.awt.Toolkit

class App : GameApplication() {
    override fun initSettings(settings: GameSettings) {
        val d = Toolkit.getDefaultToolkit().screenSize
        val origW = d.width
        val origH = d.height

        if (Config.FULL_SCREEN) {
            settings.isFullScreenFromStart = true
            settings.isFullScreenAllowed = true
            settings.width = origW
            settings.height = origH
            val scale = ((origW * 1f / Config.SCREEN_WIDTH + origH * 1f / Config.SCREEN_HEIGHT) / 2).toDouble()
            Config.SCREEN_WIDTH = origW
            Config.SCREEN_HEIGHT = origH
            Config.CARD_WIDTH *= scale
            Config.CARD_HEIGHT *= scale
        } else {
            settings.width = Config.SCREEN_WIDTH
            settings.height = Config.SCREEN_HEIGHT
        }

        settings.title = "knight"
        settings.version = "0.1"
        settings.isIntroEnabled = false
        settings.isProfilingEnabled = false
        settings.applicationMode = ApplicationMode.DEVELOPER
        settings.isDeveloperMenuEnabled = false
        settings.defaultLanguage = Language.ENGLISH
        settings.supportedLanguages = listOf(Language.CHINESE, Language.ENGLISH)
        settings.sceneFactory = GameSceneFactory()
        settings.isMainMenuEnabled = false
        settings.isGameMenuEnabled = false
        settings.cssList = mutableListOf("fxgl_dark.css", "ui.css")
//        settings.is3D = true
    }

    override fun initGame() {
        super.initGame()
        runOnce({ Game.switchScene(DungeonScene()) }, Duration.ZERO)
    }

    override fun initUI() {
        super.initUI()
    }

    override fun onUpdate(tpf: Double) {
        super.onUpdate(tpf)
    }


}

fun main(args: Array<String>) {
    if (Config.DEBUG) {
        GlobalCheck.check()
    }
    GameApplication.launch(App::class.java, args)
}