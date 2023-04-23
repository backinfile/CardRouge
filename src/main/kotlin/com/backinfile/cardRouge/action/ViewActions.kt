package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup

object ViewActions {
    suspend fun Context.refreshHandPile() {
        if (human !is Player) return
        val duration = BoardHandPileGroup.refreshHandCardAction(human.handPile.toList())
        board.waitTime(duration)
    }
}