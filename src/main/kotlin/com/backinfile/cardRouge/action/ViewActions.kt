package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.HumanBase
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup
import com.backinfile.support.kotlin.once
import kotlin.properties.Delegates

object ViewActions {
    suspend fun ActionContext.refreshHandCardAction() {
        val duration = BoardHandPileGroup.refreshHandCardAction(board.getPlayer().handPile.toList())
        board.waitTime(duration)
    }
}