package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.viewGroup.BaseSingleViewGroup
import com.backinfile.cardRouge.viewGroup.Param
import javafx.util.Duration
import java.util.*

object BoardHandPileGroup : BaseSingleViewGroup<Param>() {
    private val cardsInOrder = LinkedList<Card>()
    private var hovered: Card? = null
    private var draged: Card? = null

    var flow = false
        set(value) {
            field = value; reCalcHandCardInfo()
        }

    fun refreshHandCardAction(cards: List<Card>): Duration {
        cardsInOrder.clear()
        cardsInOrder += cards
        hovered = null;
        draged = null;
        return reCalcHandCardInfo()
    }

    fun enablePlay(enable: Boolean = true) {
        for (card in cardsInOrder) {
            val cardView = CardViewManager.getOrCreate(card)
            cardView.modInteract.disableAll()
            cardView.modInteract.enableMouseOver(true, {
                hovered = card;
                reCalcHandCardInfo();
                cardView.modInteract.enableDrag(true, {
                    draged = card
                    reCalcHandCardInfo()
                }, {

                }, {
                    hovered = null
                    draged = null
                    reCalcHandCardInfo()
                }, {
                    hovered = null
                    draged = null
                    reCalcHandCardInfo()
                })
            }, {
                hovered = null;
                reCalcHandCardInfo();
                cardView.modInteract.enableDrag(false)
            })
        }
    }

    fun updateCardViewOperationState() {

    }

    private fun reCalcHandCardInfo(): Duration {
        Log.game.info("reCalcHandCardInfo")
        var maxDuration = Duration.ZERO
        for ((index, card) in cardsInOrder.withIndex()) {
            val cardView = CardViewManager.getOrCreate(card)

            val cardState = when {
                draged == cardView.card -> HandPositionUtils.CardState.Drag
                hovered == cardView.card -> HandPositionUtils.CardState.HandHover
                flow -> HandPositionUtils.CardState.HandFlow
                else -> HandPositionUtils.CardState.HandNormal
            }

            val info = HandPositionUtils.CardInfo(cardState, index, cardsInOrder.size)

            val ani = HandPositionUtils.setCardState(cardView, info)
            maxDuration = maxOf(maxDuration, ani)
        }
        return maxDuration
    }


}