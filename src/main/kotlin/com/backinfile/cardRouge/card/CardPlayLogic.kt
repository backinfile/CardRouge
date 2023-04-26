package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.action.Actions.moveCardToDiscardPile
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.action.ViewActions.refreshHandPileView
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.human.Player
import javafx.geometry.Point2D


object CardPlayLogic {


    fun calcCardPlayableState(context: Context, card: Card): CardPlayTargetInfo? {
        card.playTargetInfo = null

        if (card.confCard.cardType == GameConfig.CARD_TYPE_UNIT) {
            return CardPlayTargetInfo(selectSlotAsMinion = true).also { card.playTargetInfo = it }
        }
        return null
    }


    suspend fun Context.playCard(card: Card, target: List<Card>) {
        if (human !is Player) return
        human.handPile.remove(card)

        // cost mana
        // card effect

        if (card.confCard.cardType == GameConfig.CARD_TYPE_ACTION) {
            moveCardToDiscardPile(card)
        }
        refreshHandPileView()
    }
}