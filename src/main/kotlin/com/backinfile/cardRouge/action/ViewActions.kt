package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.action.OperationActions.waitPressTurnEnd
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup
import com.backinfile.support.SysException
import javafx.geometry.Point2D

object ViewActions {
    suspend fun Context.refreshHandPileView() {
        if (human !is Player) return
        val duration = BoardHandPileGroup.refreshHandCardAction(human.handPile.toList())
        board.waitTime(duration)
    }

    suspend fun Context.updatePileNumber() {

    }

    suspend fun Context.selectCardTarget(card: Card): List<Card> {
        if (human !is Player) throw SysException("")
        val playTargetInfo = card.playTargetInfo ?: return listOf()

        if (playTargetInfo.selectSlotAsMinion) {
            val cardView = CardViewManager.getOrCreate(card)
            val duration = cardView.modMove.move(
                pos = Point2D(Config.SCREEN_WIDTH * 0.1, Config.SCREEN_HEIGHT * 0.9),
                scale = Config.SCALE_SLOT_CARD,
                duration = Config.ANI_CARD_MOVE_TIME
            )
            cardView.shapeTo(cardView.shape.copy(minion = true))

            board.waitTime(duration)


            for (slot in human.slots.values) {
                if (slot.isEmpty(true)) {
                    val slotView = CardViewManager.getOrCreate(slot.crystal)
                    slotView.modView.setGlow(true)
                }
            }


        }

        waitPressTurnEnd()

        return listOf()
    }
}