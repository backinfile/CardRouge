package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.*
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.viewGroups.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Point2D

// 动画动作，没有数据改变
object ViewActions {
    suspend fun Context.viewRefreshHandPileView() {
        if (human !is Player) return
        val duration = BoardHandPileGroup.refreshHandCardAction(human.handPile.toList())
        board.waitTime(duration)
    }

    suspend fun Context.viewUpdatePileNumber() {

    }


    suspend fun Context.viewAttack(card: Card, targetSlotIndex: Int) {
    }

    suspend fun Context.viewCreateCardViewAtDrawPile(card: Card): CardView {
        val cardView = CardViewManager.getOrCreate(card)
        cardView.modMove.move(
            pos = Point2D(Config.SCREEN_WIDTH * 0.9, Config.SCREEN_HEIGHT * 0.9),
            scale = 0.1,
            rotate = 0.0,
            viewOrder = ViewOrder.PILE_ICON.viewOrder() - 1
        )
        return cardView
    }


    suspend fun Context.viewMoveCardToSlot(slotIndex: Int, card: Card) {
        val cardSlot = human.slots[slotIndex]!!

        val cardView = CardViewManager.getOrCreate(card)
        cardView.modMove.move(
            pos = cardSlot.position.toPoint2D(),
            scale = ViewConfig.SCALE_SLOT_CARD,
            rotate = 0.0,
            viewOrder = ViewOrder.CARD_BOARD.viewOrder(),
            duration = ViewConfig.ANI_CARD_MOVE_TIME
        )
        board.waitTime(ViewConfig.ANI_CARD_MOVE_TIME)
    }

    suspend fun Context.viewMoveCardToDiscardPile(card: Card) {
        val cardView = CardViewManager.getOrCreate(card)
        cardView.modMove.move(
            pos = Point2D(0.0, 0.0),
            scale = 0.0,
            viewOrder = ViewOrder.PILE_ICON.viewOrder(),
            duration = ViewConfig.ANI_CARD_MOVE_TIME
        )
        board.waitTime(ViewConfig.ANI_CARD_MOVE_TIME)
        CardViewManager.remove(card)
    }


    data class SelectTargetResult(
        val targetCards: List<Card> = listOf(),
        val targetSlotIndex: Int = -1,
        val ok: Boolean = true
    )

    suspend fun Context.viewSelectCardTarget(card: Card, curSlotIndex: Int): SelectTargetResult {
        if (human !is Player) throw SysException("")
        val playTargetInfo = card.playTargetInfo ?: return SelectTargetResult(ok = false)

        val selectedCards = ArrayList<Card>()
        var canceled = false
        var comfirmed = false

        // 先移动当前卡到合适的位置
        if (playTargetInfo.playMinion) { // 随从移动到该位置
            val cardView = CardViewManager.getOrCreate(card)
            val duration = cardView.modMove.move(
                pos = human.slots[curSlotIndex]!!.position.toPoint2D(),
                scale = ViewConfig.SCALE_SLOT_CARD,
                duration = ViewConfig.ANI_CARD_MOVE_TIME
            )
            board.waitTime(duration)
        } else { // 行动卡移动到屏幕一角
            val cardView = CardViewManager.getOrCreate(card)
            val duration = cardView.modMove.move(
                pos = Point2D(Config.SCREEN_WIDTH * 0.1, Config.SCREEN_HEIGHT * 0.9),
                scale = ViewConfig.SCALE_SLOT_CARD,
                duration = ViewConfig.ANI_CARD_MOVE_TIME
            )
            cardView.shapeTo(cardView.shape.copy(minion = true))
            board.waitTime(duration)
        }

        // 然后选择目标卡牌
        val stepSelectFunc = playTargetInfo.stepSelectFunc
        if (stepSelectFunc != null) { // 打出需要选择目标
            while (true) {
                val selectTrigger = SimpleBooleanProperty(false)
                val result = stepSelectFunc(selectedCards)

                if (result.error != null) {
                    Log.card.error("error:${result.error}")
                    return SelectTargetResult(ok = false)
                }
                // 标记可选择卡牌
                for (card in human.getAllVisibleCards()) {
                    val cardView = CardViewManager.getOrCreate(card)
                    if (card in selectedCards) {
                        if (result.cancelable) {
                            cardView.modInteract.enableClick { selectedCards.remove(card);selectTrigger.set(true) }
                            cardView.modView.setGlow(true)
                        } else {
                            cardView.modInteract.disableAll()
                            cardView.modView.setGlow(false)
                        }
                    } else if (card in result.nextSelectFrom) {
                        cardView.modInteract.enableClick { selectedCards.add(card);selectTrigger.set(true) }
                        cardView.modView.setGlow(true)
                    } else {
                        cardView.modInteract.disableAll()
                        cardView.modView.setGlow(false)
                    }
                }
                // 确认按钮
                BoardButtonsUIGroup.show(
                    ButtonsParam(
                        ButtonInfo(Res.TEXT_CANCEL, { canceled = true; selectTrigger.set(true) }, result.optional),
                        ButtonInfo(Res.TEXT_CONFIRM, { comfirmed = true; selectTrigger.set(true) }, result.optional),
                    )
                )

                // 等待选择一张卡
                board.waitCondition(selectTrigger)
                if (canceled) return SelectTargetResult(ok = false)
                if (comfirmed) break
            }
        }

        // 打出后清理
        BoardButtonsUIGroup.hide()
        for (card in human.getAllVisibleCards()) {
            val cardView = CardViewManager.getOrCreate(card)
            cardView.modView.setGlow(false)
            cardView.modInteract.disableAll()
        }

        return SelectTargetResult(targetCards = selectedCards, targetSlotIndex = curSlotIndex, ok = true)
    }
}