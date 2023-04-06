package com.backinfile.cardRouge

import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings

class App : GameApplication() {
    override fun initSettings(settings: GameSettings) {
        with(settings) {
            width = 720
            height = 640
            title = "Kotlin Game - Target Practice"
        }
    }
}

fun main(args: Array<String>) {
    GameApplication.launch(App::class.java, args)
}