package com.backinfile.cardRouge

import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.getGameScene
import com.almasb.fxgl.scene.SubScene
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.human.Robot
import com.backinfile.cardRouge.viewGroups.BoardBackgroundGroup

object Game {
    var curScene: SubScene? = null
        private set

    fun getInput() = curScene?.input ?: FXGL.getInput()

    fun getScene() = curScene ?: getGameScene()


//    fun switchScene(subScene: SubScene) {
//        if (curScene != null) FXGL.getSceneService().popSubScene()
//        FXGL.getSceneService().pushSubScene(subScene)
//        curScene = subScene
////        getInput().set
//    }

    fun startUp() {
        Res.loadAll()


//            val card = Card(ConfCard.get(1201001))
//            val cardView = CardViewManager.getOrCreate(card)

//            BoardButtonsUIGroup.show(ButtonsParam(ButtonInfo(Res.TEXT_CONFIRM)))
        BoardBackgroundGroup.show()
        FXGL.getInput().clearAll()

//        BoardButtonsUIGroup.show(ButtonsParam(ButtonInfo(Res.TEXT_CONFIRM) {
//            Log.game.info("onclick")
//        }))

//        getGameScene().addUINode(ImageView("/assets/textures/room/room_change.png"))
        startBoard()
    }

    private fun startBoard() {
        val dungeon = Dungeon()
        val board = Board()

        val player = Player()
        val robot = Robot()

        board.dungeon = dungeon
        board.init(player, robot)

        getGameScene().addListener(board)
    }

    fun update(delta: Double) {
        CardViewManager.update(delta)
    }

    fun getDungeon(): Dungeon {
        return Dungeon()
    }
}