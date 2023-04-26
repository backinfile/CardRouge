package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.action.Actions.moveCardToDiscardPile
import com.backinfile.cardRouge.action.Actions.summonTo
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.action.ViewActions.refreshHandPileView
import com.backinfile.cardRouge.action.ViewActions.selectCardTarget
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup
import com.backinfile.support.async.runAsync


object CardPlayLogic {


    fun calcCardPlayableState(context: Context, card: Card): CardPlayTargetInfo? {
        card.playTargetInfo = null

        if (card.confCard.cardType == GameConfig.CARD_TYPE_UNIT) {
            return CardPlayTargetInfo(selectSlotAsMinion = true).also { card.playTargetInfo = it }
        }
        return null
    }

    /**
     * @return played
     */
    fun handleDragPlayEnd(context: Context, card: Card): Boolean {
        if (card.playTargetInfo?.selectSlotAsMinion == true) { // 打出随从
            runAsync {
                val selectResult = context.selectCardTarget(card)
                if (selectResult == null) {
                    BoardHandPileGroup.enablePlay(true, context)
                } else {
                    // 成功打出
                    playCard(context, card, selectResult)
                    BoardHandPileGroup.enablePlay(true, context)
                }
            }
            return true
        }
        return false
    }


    suspend fun playCard(context: Context, card: Card, target: List<Card>) = with(context) {
        if (human !is Player) return
        human.handPile.remove(card)

        // cost mana
        human.mana = maxOf(0, human.mana - card.manaCost)

        // card effect
        if (card.confCard.cardType == GameConfig.CARD_TYPE_UNIT) {
            assert(target.size == 1)
            val targetSlotIndex = human.getCrystalSlotIndex(target.first())
            summonTo(targetSlotIndex, card)
        }

        if (card.confCard.cardType == GameConfig.CARD_TYPE_ACTION) {
            moveCardToDiscardPile(card)
        }
        refreshHandPileView()
    }
}