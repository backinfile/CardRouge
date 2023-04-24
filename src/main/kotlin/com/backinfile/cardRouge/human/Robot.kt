package com.backinfile.cardRouge.human

import com.backinfile.cardRouge.action.Actions.changeBoardStateTo
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.Card

class Robot : HumanBase() {

    lateinit var mainMonster: Card;

    override fun isPlayer() = false


    override suspend fun playInTurn() {
        board.changeBoardStateTo(Board.State.TurnAfter)
    }

}