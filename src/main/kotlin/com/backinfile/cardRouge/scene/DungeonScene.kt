package com.backinfile.cardRouge.scene

import com.almasb.fxgl.core.math.Vec2
import com.almasb.fxgl.dsl.getGameScene
import com.almasb.fxgl.scene.SubScene
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Game
import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.gen.config.ConfCard
import com.backinfile.cardRouge.viewGroup.ViewGroupManager
import com.backinfile.cardRouge.viewGroups.BoardBackgroundGroup
import javafx.geometry.Point2D
import javafx.util.Duration

class DungeonScene : SubScene() {

    override fun onCreate() {
        super.onCreate()

        ViewGroupManager.show(BoardBackgroundGroup::class)

        val card = Card(ConfCard.get(GameConfig.CARD_ID_CRYSTAL))

        val cardView = CardViewManager.getOrCreate(card)
//        cardView.moveInfo.move(Point2D(1000.0, 300.0), scale = 0.3, rotate = 180.0, duration = Duration.seconds(0.2))
        cardView.moveInfo.move(Point2D(1000.0, 300.0), duration = Duration.seconds(0.2))
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onUpdate(tpf: Double) {
        super.onUpdate(tpf)

        Game.update(tpf)
    }
}