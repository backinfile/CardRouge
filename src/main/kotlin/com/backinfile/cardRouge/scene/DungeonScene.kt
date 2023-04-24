package com.backinfile.cardRouge.scene

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.scene.SubScene
import com.backinfile.cardRouge.*
import com.backinfile.cardRouge.viewGroups.BoardBackgroundGroup
import com.backinfile.cardRouge.viewGroups.BoardButtonsUIGroup
import com.backinfile.cardRouge.viewGroups.ButtonInfo
import com.backinfile.cardRouge.viewGroups.ButtonsParam
import javafx.scene.shape.Rectangle
import javafx.util.Duration

class DungeonScene : SubScene() {

    override fun onCreate() {
        super.onCreate()

        BoardBackgroundGroup.show()

        FXGL.getInput().clearAll()
        BoardButtonsUIGroup.show(ButtonsParam(ButtonInfo(Res.TEXT_CONFIRM)))

//        Views.show(BoardBackgroundGroup::class)
//
//        val card = Card(ConfCard.get(1201001))
//
//        val cardView = CardViewManager.getOrCreate(card)



//        cardView.modMove.move(scale = Config.SCALE_DISCOVER_CARD)
//////        cardView.moveInfo.move(Point2D(1000.0, 300.0), scale = 0.3, rotate = 180.0, duration = Duration.seconds(0.2))
//        cardView.modMove.move(Point2D(1000.0, 300.0), duration = Duration.seconds(0.2))
////        cardView.moveInfo.move(Point2D(1000.0, 300.0), duration = Duration.ZERO)
//
//        cardView.modInteract.enableDrag(true).enableClick {
//            Log.game.info("click")
//        }


        BoardButtonsUIGroup.show(ButtonsParam(ButtonInfo(Res.TEXT_CONFIRM)))


//        val group = Group();
//        Game.getScene().addChild(group)
//
//        val card2 = Card(ConfCard.get(GameConfig.CARD_ID_CRYSTAL))
//        CardViewManager.getOrCreate(card2).modInteract.enableDrag(true)
//
//
//        val dungeon = Dungeon()
//
//        val board = Board()
//        board.dungeon = dungeon
//        val player = Player()
//        repeat(10) { player.drawPile.addCard(Card(ConfCard.get(1201001))) }
//        board.init(player, Robot())
//        addListener(board)

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