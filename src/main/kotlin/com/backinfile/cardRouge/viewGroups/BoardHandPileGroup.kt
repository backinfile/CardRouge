package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.action.Context
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
                    val player = context.human
                    var selectSlotIndex = -1;
                    cardView.modInteract.enableDrag(
                        true,
                        start = {
                            draged = card
                            reCalcHandCardState(card)
                        },
                        update = {
                            val nearestSlot =
                                SlotViewUtils.findNearestSlot(player.slots, cardView.modMove.position.value, 10.0)
                            if (nearestSlot != selectSlotIndex) {
                                if (selectSlotIndex >= 0) {
                                    CardViewManager.getOrCreate(player.slots[selectSlotIndex]!!.crystal).modView.setGlow(false)
                                }
                                if (nearestSlot >= 0) {
                                    CardViewManager.getOrCreate(player.slots[nearestSlot]!!.crystal).modView.setGlow(true)
                                }
                                selectSlotIndex = nearestSlot
                            }
                        },
                        over = {
                            runAsync {
                                if (selectSlotIndex >= 0) {
                                    CardViewManager.getOrCreate(player.slots[selectSlotIndex]!!.crystal).modView.setGlow(false)
                                    if (CardPlayLogic.handleDragPlayEnd(context, card, selectSlotIndex)) { // 成功打出
                                        return@runAsync
                                    }
                                }
                                // 没有打出
                                hovered = null
                                draged = null
                                reCalcHandCardState(card)
                            }
                        },
                        cancel = {
                            hovered = null
                            draged = null
                            reCalcHandCardState(card)
                        },
                    )
                } else { // 拖拽打出行动
                    cardView.modInteract.enableDrag(
                        true,
                        start = {
                            draged = card
                            reCalcHandCardState(card)
                        },
                        update = {
                        },
                        over = {
                            runAsync {
                                if (cardView.modMove.position.value.y < Config.SCREEN_HEIGHT * 0.7) {
                                    if (CardPlayLogic.handleDragPlayEnd(context, card)) { // 成功打出
                                        return@runAsync
                                    }
                                }
                                // 没有打出
                                hovered = null
                                draged = null
                                reCalcHandCardState(card)
                            }
                        },
                        cancel = {
                            hovered = null
                            draged = null
                            reCalcHandCardState(card)
                        },
                    )
                }
            } else {
                // 只允许一点拖拽，超出后复原
                cardView.modInteract.enableDrag(
                    true,
                    start = {
                        draged = card
                        reCalcHandCardState(card)
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
                        reCalcHandCardState(card)
                    },
                    cancel = {
                        hovered = null
                        draged = null
                        reCalcHandCardState(card)
                    },
                )
            }
        }
    }

    fun updateCardViewOperationState() {

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
}