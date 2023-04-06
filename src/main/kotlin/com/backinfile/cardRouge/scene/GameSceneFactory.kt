package com.backinfile.cardRouge.scene

import com.almasb.fxgl.app.scene.*

class GameSceneFactory : SceneFactory() {
    override fun newGameMenu(): FXGLMenu {
        return super.newGameMenu()
    }

    override fun newLoadingScene(): LoadingScene {
        return super.newLoadingScene()
    }

    override fun newMainMenu(): FXGLMenu {
        return FXGLDefaultMenu(MenuType.MAIN_MENU)
    }

    override fun newStartup(width: Int, height: Int): StartupScene {
        return super.newStartup(width, height)
    }
}
