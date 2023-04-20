package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.viewGroup.BaseSingleViewGroup
import com.backinfile.cardRouge.viewGroup.Param
import com.backinfile.support.kotlin.maxOf
import javafx.util.Duration
import java.util.*

object BoardHandPileGroup : BaseSingleViewGroup<Param>() {
    private val cardInfoCacheMap = HashMap<CardView, HandPositionUtils.CardInfo>()
    private val cardsInOrder = LinkedList<Card>()
    private var hovered: Card? = null
    private var draged: Card? = null

    var flow = false
        set(value) {
            reCalcHandCardInfo(); field = value
        }

    var keepFlow = false
        set(value) {
            reCalcHandCardInfo(); field = value
        }

    fun refreshHandCardAction(cards: List<Card>): Duration {
        cardsInOrder.clear()
        cardsInOrder += cards
        return reCalcHandCardInfo()
    }

    fun updateCardViewOperationState() {

    }

    private fun reCalcHandCardInfo(): Duration {
        var maxDuration = Duration.ZERO

        cardInfoCacheMap.clear()
        for ((index, card) in cardsInOrder.withIndex()) {
            val cardView = CardViewManager.getOrCreate(card)
            val ani = HandPositionUtils.setCardState(cardView, HandPositionUtils.CardInfo(HandPositionUtils.CardState.HandNormal, index, cardsInOrder.size))
            maxDuration = maxOf(maxDuration, ani) { it.toMillis() }
//            cardInfoCacheMap[cardView] = CardInfo(CardState.HandNormal, index, cardsInOrder.size)
        }
        return maxDuration
    }


}