package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup

object ViewActions {
    suspend fun Context.refreshHandCardAction() {
        val duration = BoardHandPileGroup.refreshHandCardAction(board.getPlayer().handPile.toList())
        board.waitTime(duration)
    }
}