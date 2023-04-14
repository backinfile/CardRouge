package com.backinfile.cardRouge.scene

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.scene.SubScene
import com.backinfile.cardRouge.*
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

        BoardBackgroundGroup.show()

        FXGL.getInput().clearAll()

//        Views.show(BoardBackgroundGroup::class)

        val card = Card(ConfCard.get(1201001))

        val cardView = CardViewManager.getOrCreate(card)
        cardView.modMove.move(scale = Config.SCALE_HAND_CARD)
////        cardView.moveInfo.move(Point2D(1000.0, 300.0), scale = 0.3, rotate = 180.0, duration = Duration.seconds(0.2))
        cardView.modMove.move(Point2D(1000.0, 300.0), duration = Duration.seconds(0.2))
//        cardView.moveInfo.move(Point2D(1000.0, 300.0), duration = Duration.ZERO)

        cardView.modInteract.enableDrag().enableClick {
            Log.game.info("click")
        }

        BoardButtonsUIGroup.show()

//        Views.show(BoardButtonsUIGroup::class, ButtonsParam(ButtonInfo(Res.TEXT_CLOSE)))

//        Views.show(BoardButtonsUIGroup::class, ButtonsParam(ButtonInfo(Res.TEXT_CLOSE)))
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onUpdate(tpf: Double) {
        super.onUpdate(tpf)

        Game.update(tpf)
    }
}