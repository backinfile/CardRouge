package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.cardView.CardMoveInfo
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewBaseMod

class ModMove(cardView: CardView) : CardViewBaseMod(cardView) {
    val moveInfo = CardMoveInfo()

    override fun onCreate() {
        super.onCreate()
        moveInfo.bindBy(cardView)
    }

    override fun update(delta: Double) {
        moveInfo.update(delta)
    }
}