package com.backinfile.cardRouge.scene

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.scene.SubScene
import com.backinfile.cardRouge.Game
import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.gen.config.ConfCard
import com.backinfile.cardRouge.viewGroup.Views
import com.backinfile.cardRouge.viewGroups.BoardBackgroundGroup
import com.backinfile.cardRouge.viewGroups.BoardButtonsUIGroup
import com.backinfile.cardRouge.viewGroups.ButtonInfo
import com.backinfile.cardRouge.viewGroups.ButtonsParam
import com.backinfile.support.fxgl.FXGLUtils
import javafx.application.Platform
import javafx.geometry.Point2D
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import javafx.util.Duration

class DungeonScene : SubScene() {

    override fun onCreate() {
        super.onCreate()



        FXGL.getInput().clearAll()

//        Views.show(BoardBackgroundGroup::class)

        val card = Card(ConfCard.get(1201001))

        val cardView = CardViewManager.getOrCreate(card)
////        cardView.moveInfo.move(Point2D(1000.0, 300.0), scale = 0.3, rotate = 180.0, duration = Duration.seconds(0.2))
        cardView.moveInfo.move(Point2D(1000.0, 300.0), duration = Duration.seconds(0.2))
//        cardView.moveInfo.move(Point2D(1000.0, 300.0), duration = Duration.ZERO)

        cardView.modInteract.enableDrag().enableClick {
            Log.game.info("click")
        }

//        Views.show(BoardButtonsUIGroup::class, ButtonsParam(ButtonInfo(Res.TEXT_CLOSE)))


        // 开始界面
        val vBox: VBox = VBox(
            FXGLUtils.btn("开始") {  },
            FXGLUtils.btn("设置") { FXGL.getSceneService().pushSubScene(SettingScene.Instance) },
            FXGLUtils.btn("退出") { Platform.exit() }
        )
        vBox.alignment = Pos.CENTER
        vBox.translateXProperty().bind(vBox.widthProperty().multiply(-0.5f).add(FXGL.getAppWidth() / 4f))
        vBox.translateYProperty().bind(vBox.heightProperty().multiply(-0.5f).add(FXGL.getAppHeight() / 2f))
        FXGL.getGameScene().addUINode(vBox)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onUpdate(tpf: Double) {
        super.onUpdate(tpf)

        Game.update(tpf)
    }
}