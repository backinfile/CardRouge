package com.backinfile.cardRouge.human

import com.backinfile.cardRouge.action.Actions.changeBoardStateTo
import com.backinfile.cardRouge.board.Board

class Robot : HumanBase() {
    override fun isPlayer() = false


    override suspend fun playInTurn() {
        board.changeBoardStateTo(Board.State.TurnAfter)
    }

}