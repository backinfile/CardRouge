package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Game
import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.action.ViewActions.selectCardTarget
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardPlayLogic
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.viewGroup.BaseSingleViewGroup
import com.backinfile.cardRouge.viewGroup.Param
import com.backinfile.support.async.runAsync
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

    fun enablePlay(enable: Boolean = true, context: Context? = null) {
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
            cardView.modInteract.disableAll()


            // 鼠标滑动到此放大卡牌
            cardView.modInteract.enableMouseOver(true, {
                hovered = card;
                reCalcHandCardInfo(card);
            }, {
                hovered = null;
                reCalcHandCardInfo(card);
            })

            if (card.playTargetInfo != null) {
                cardView.modView.setGlow()

                // 拖拽打出
                cardView.modInteract.enableDrag(true,
                    start = {
                        draged = card
                        reCalcHandCardInfo(card)
                    },
                    update = {
                        if (card.playTargetInfo?.selectSlotAsMinion == true) { // 打出随从
                            if (cardView.modMove.position.value.y < Config.SCREEN_HEIGHT * 0.7) {
                                cardView.modInteract.enableDrag(false, triggerCancel = false)
                                enablePlay(false)
                                runAsync { context!!.selectCardTarget(card) }
                            }
                        }
                    },
                    over = {
                        hovered = null
                        draged = null
                        reCalcHandCardInfo(card)
                    },

                    cancel = {
                        hovered = null
                        draged = null
                        reCalcHandCardInfo(card)
                    })
            } else {
                // 只允许一点拖拽，超出后复原
                cardView.modInteract.enableDrag(true,
                    start = {
                        draged = card
                        reCalcHandCardInfo(card)
                    },
                    update = {
                        if (cardView.modMove.position.value.y < Config.SCREEN_HEIGHT * 0.7) {
                            cardView.modInteract.enableDrag(false)
                            cardView.modInteract.enableDrag(true)
                        }
                    },
                    over = {
                        hovered = null
                        draged = null
                        reCalcHandCardInfo(card)
                    },

                    cancel = {
                        hovered = null
                        draged = null
                        reCalcHandCardInfo(card)
                    })
            }
        }
    }

    fun updateCardViewOperationState() {

    }

    private fun reCalcHandCardInfo(): Duration {
        var maxDuration = Duration.ZERO
        for (card in cardsInOrder) {
            val ani = reCalcHandCardInfo(card)
            maxDuration = maxOf(maxDuration, ani)
        }
        return maxDuration
    }

    private fun reCalcHandCardInfo(card: Card): Duration {
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
}