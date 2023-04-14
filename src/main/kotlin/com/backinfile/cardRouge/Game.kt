package com.backinfile.cardRouge

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.getInput
import com.almasb.fxgl.scene.SubScene
import com.backinfile.cardRouge.cardView.CardViewManager

object Game {
    var curScene: SubScene? = null
        private set

    fun getInput() = curScene?.input ?: FXGL.getInput()

    fun switchScene(subScene: SubScene) {
        if (curScene != null) FXGL.getSceneService().popSubScene()
        FXGL.getSceneService().pushSubScene(subScene)
        curScene = subScene
//        getInput().set
    }

    fun update(delta: Double) {
        CardViewManager.update(delta)
    }
}