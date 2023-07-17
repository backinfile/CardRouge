package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.ViewConfig
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardPlayLogic
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.cardView.mods.CardDragCallback
import com.backinfile.cardRouge.human.HumanBase
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.viewGroup.BaseSingleViewGroup
import com.backinfile.cardRouge.viewGroup.Param
import com.backinfile.support.async.runAsync
import javafx.util.Duration
import java.util.*

object BoardHandPileGroup : BaseSingleViewGroup<Param>() {
    private val cardsInOrder = LinkedList<Card>()
    private var hovered: Card? = null
    private var draged: Card? = null

    var flow = true
        set(value) {
            field = value; reCalcHandCardState()
        }

    fun refreshHandCardAction(cards: List<Card>): Duration {
        cardsInOrder.clear()
        cardsInOrder += cards
        hovered = null;
        draged = null;
        return reCalcHandCardState()
    }

    fun enablePlay(enable: Boolean = true, context: Context) {
        if (!enable) {
            for (card in cardsInOrder) {
                val cardView = CardViewManager.getOrCreate(card)
                cardView.modInteract.disableAll()
                cardView.modView.setGlow(false)
            }
            return
        }

        for (card in cardsInOrder) {
            val cardView = CardViewManager.getOrCreate(card)
//            cardView.modInteract.disableAll()

            val playTargetInfo = card.playTargetInfo
            if (playTargetInfo != null) {
                cardView.modView.setGlow()

                // 鼠标滑动到此放大卡牌
                cardView.modInteract.enableMouseOver(true, {
                    hovered = card;
                    reCalcHandCardState(card);
                }, {
                    hovered = null;
                    reCalcHandCardState(card);
                })

                if (playTargetInfo.playMinion) { // 拖拽打出随从
                    cardView.modInteract.enableDrag(true, DragPlayMinionCallback(context.human as Player, context))
                } else { // 拖拽打出行动
                    cardView.modInteract.enableDrag(true, DragPlayActionCallback(context.human as Player, context))
                }
            } else {
                // 只允许一点拖拽，超出后复原
                cardView.modInteract.enableDrag(true, DragFakeCallback())
            }
        }
    }

    private fun reCalcHandCardState(): Duration {
        var maxDuration = Duration.ZERO
        for (card in cardsInOrder) {
            val ani = reCalcHandCardState(card)
            maxDuration = maxOf(maxDuration, ani)
        }


        for (card in cardsInOrder) {
            val cardView = CardViewManager.getOrCreate(card)
            // 鼠标滑动到此放大卡牌
            cardView.modInteract.enableMouseOver(true, {
                hovered = card;
                reCalcHandCardState(card);
            }, {
                hovered = null;
                reCalcHandCardState(card);
            })
        }
        return maxDuration
    }

    private fun reCalcHandCardState(card: Card): Duration {
        val index = cardsInOrder.indexOf(card)
        if (index < 0) return Duration.ZERO

        val cardView = CardViewManager.getOrCreate(card)
        val cardState = when {
            draged == cardView.card -> HandPositionUtils.CardState.Drag
            hovered == cardView.card -> HandPositionUtils.CardState.HandHover
            flow -> HandPositionUtils.CardState.HandFlow
            else -> HandPositionUtils.CardState.HandNormal
        }
        val info = HandPositionUtils.CardInfo(cardState, index, cardsInOrder.size)
        return HandPositionUtils.setCardState(cardView, info)
    }

    private class DragPlayMinionCallback(private val player: Player, private val context: Context) : CardDragCallback {

        private var selectSlotIndex = -1

        override fun start(cardView: CardView) {
            super.start(cardView)
            draged = cardView.card
            cardView.shapeTo(cardView.shape.copy(minion = true))
            reCalcHandCardState(cardView.card)
        }

        override fun update(cardView: CardView) {
            super.update(cardView)

            val nearestSlot =
                SlotViewUtils.findNearestSlot(
                    player.slots,
                    cardView.modMove.position.value,
                    ViewConfig.CARD_WIDTH * ViewConfig.SCALE_DRAG_CARD / 2,
                )
            if (nearestSlot != selectSlotIndex) {
                if (selectSlotIndex >= 0) {
                    val crystal = player.slots[selectSlotIndex]!!.crystal
                    CardViewManager.getOrCreate(crystal).modView.setGlow(false)
                }
                if (nearestSlot >= 0) {
                    val crystal = player.slots[nearestSlot]!!.crystal
                    CardViewManager.getOrCreate(crystal).modView.setGlow(true)
                }
                selectSlotIndex = nearestSlot
            }
        }

        override fun over(cardView: CardView) {
            super.over(cardView)
            runAsync {
                if (selectSlotIndex >= 0) {
                    CardViewManager.getOrCreate(player.slots[selectSlotIndex]!!.crystal).modView.setGlow(false)
                    if (CardPlayLogic.handleDragPlayEnd(context, cardView.card, selectSlotIndex)) { // 成功打出
                        return@runAsync
                    }
                }
                // 没有打出
                hovered = null
                draged = null
                cardView.shapeTo(cardView.shape.copy(minion = false))
                reCalcHandCardState(cardView.card)
            }
        }

        override fun cancel(cardView: CardView) {
            super.cancel(cardView)
            hovered = null
            draged = null
            cardView.shapeTo(cardView.shape.copy(minion = false))
            reCalcHandCardState(cardView.card)
        }
    }

    private class DragPlayActionCallback(private val player: Player, private val context: Context) : CardDragCallback {

        override fun start(cardView: CardView) {
            super.start(cardView)
            val card = cardView.card
            draged = card
            reCalcHandCardState(card)
        }

        override fun update(cardView: CardView) {
            super.update(cardView)
        }

        override fun over(cardView: CardView) {
            super.over(cardView)
            runAsync {
                if (cardView.modMove.position.value.y < Config.SCREEN_HEIGHT * 0.7) {
                    if (CardPlayLogic.handleDragPlayEnd(context, cardView.card)) { // 成功打出
                        return@runAsync
                    }
                }
                // 没有打出
                hovered = null
                draged = null
                reCalcHandCardState(cardView.card)
            }
        }

        override fun cancel(cardView: CardView) {
            super.cancel(cardView)
            hovered = null
            draged = null
            reCalcHandCardState(cardView.card)
        }

    }

    private class DragFakeCallback() : CardDragCallback {
        override fun start(cardView: CardView) {
            super.start(cardView)
            val card = cardView.card
            draged = card
            reCalcHandCardState(card)
        }

        override fun update(cardView: CardView) {
            super.update(cardView)
            if (cardView.modMove.position.value.y < Config.SCREEN_HEIGHT * 0.7) {
                cardView.modInteract.enableDrag(false)
                cardView.modInteract.enableDrag(true)
            }
        }

        override fun over(cardView: CardView) {
            super.over(cardView)
            hovered = null
            draged = null
            reCalcHandCardState(cardView.card)
        }

        override fun cancel(cardView: CardView) {
            super.cancel(cardView)
            hovered = null
            draged = null
            reCalcHandCardState(cardView.card)
        }
    }
}
