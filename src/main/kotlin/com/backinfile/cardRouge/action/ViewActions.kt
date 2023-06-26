package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.SysException
import com.backinfile.cardRouge.ViewOrder
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.viewGroups.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Point2D

object ViewActions {
    suspend fun Context.refreshHandPileView() {
        if (human !is Player) return
        val duration = BoardHandPileGroup.refreshHandCardAction(human.handPile.toList())
        board.waitTime(duration)
    }

    suspend fun Context.updatePileNumber() {

    }

    suspend fun Context.createCardViewAtDrawPile(card: Card): CardView {
        val cardView = CardViewManager.getOrCreate(card)
        cardView.modMove.move(
            pos = Point2D(Config.SCREEN_WIDTH * 0.9, Config.SCREEN_HEIGHT * 0.9),
            scale = 0.1,
            rotate = 0.0,
            viewOrder = ViewOrder.PILE_ICON.viewOrder() - 1
        )
        return cardView
    }

    suspend fun Context.moveCardToSlot(slotIndex: Int, card: Card) {
        val cardSlot = human.slots[slotIndex]!!

        val cardView = CardViewManager.getOrCreate(card)
        cardView.modMove.move(
            pos = cardSlot.position.toPoint2D(),
            scale = Config.SCALE_SLOT_CARD,
            rotate = 0.0,
            viewOrder = ViewOrder.CARD_BOARD.viewOrder(),
            duration = Config.ANI_CARD_MOVE_TIME
        )
        board.waitTime(Config.ANI_CARD_MOVE_TIME)
    }

    suspend fun Context.moveCardToDiscardPile(card: Card) {
        TODO()
    }

    suspend fun Context.selectCardTarget(card: Card): List<Card>? {
        if (human !is Player) throw SysException("")
        val playTargetInfo = card.playTargetInfo ?: return listOf()

        val selectOverProperty = SimpleBooleanProperty(false)
        var canceled = false
        val selectedCards = ArrayList<Card>()

        if (playTargetInfo.cancelable) {
            BoardButtonsUIGroup.show(ButtonsParam(ButtonInfo(Res.TEXT_CANCEL) {
                canceled = true
                selectOverProperty.set(true)
            }))
        } else {
            BoardButtonsUIGroup.hide()
        }


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
                    val crystal = slot.crystal
                    val slotView = CardViewManager.getOrCreate(crystal)
                    slotView.modView.setGlow(true)
                    slotView.modInteract.enableClick { selectedCards += crystal; selectOverProperty.set(true) }
                }
            }


        }

        board.waitCondition(selectOverProperty)

        BoardButtonsUIGroup.hide()
        // 清理
        for (slot in human.slots.values) {
            val slotView = CardViewManager.getOrCreate(slot.crystal)
            slotView.modInteract.disableAll()
            slotView.modView.setGlow(false)
        }

        if (canceled) {
            return null
        }
        return selectedCards
    }
}