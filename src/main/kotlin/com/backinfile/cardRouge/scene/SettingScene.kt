package com.backinfile.cardRouge.scene

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.scene.Scene
import com.almasb.fxgl.scene.SubScene
import com.backinfile.cardRouge.ViewConfig
import com.backinfile.support.fxgl.FXGLUtils
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill

class SettingScene : SubScene() {
    init {
        val width = FXGL.getAppWidth()
        val height = FXGL.getAppHeight()
        val contentRoot = contentRoot
        contentRoot.setPrefSize(width.toDouble(), height.toDouble())
        contentRoot.background = Background(
            BackgroundFill(
                ViewConfig.BACKGROUND_COLOR,
                null,
                null
            )
        )
        contentRoot.children.add(FXGLUtils.vBox(FXGL.getAppWidth() / 8f, FXGL.getAppHeight() / 2f,
            FXGLUtils.btn("setting 1") {},
            FXGLUtils.btn("setting 2") {},
            FXGLUtils.btn("setting 3") {},
            FXGLUtils.btn("return") { FXGL.getSceneService().popSubScene() }
        ))
    }

    override fun onEnteredFrom(prevState: Scene) {
        super.onEnteredFrom(prevState)
    }

    override fun onExitingTo(nextState: Scene) {
        super.onExitingTo(nextState)
    }

    override fun onUpdate(tpf: Double) {
        super.onUpdate(tpf)
    }

    companion object {
        val Instance = SettingScene()
    }
}
