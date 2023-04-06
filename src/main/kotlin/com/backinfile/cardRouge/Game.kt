package com.backinfile.cardRouge

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.scene.SubScene

object Game {
    private var curScene: SubScene? = null
    fun switchScene(subScene: SubScene) {
        if (curScene != null) FXGL.getSceneService().popSubScene()
        FXGL.getSceneService().pushSubScene(subScene)
        curScene = subScene
    }
}