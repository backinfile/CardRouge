package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.action.Actions.changeBoardStateTo
import com.backinfile.cardRouge.action.Actions.moveCardToDiscardPile
import com.backinfile.cardRouge.action.Actions.summonTo
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.action.ViewActions
import com.backinfile.cardRouge.action.ViewActions.refreshHandPileView
import com.backinfile.cardRouge.action.ViewActions.selectCardTarget
import com.backinfile.cardRouge.action.waitCondition
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.viewGroups.BoardButtonsUIGroup
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup
import com.backinfile.cardRouge.viewGroups.ButtonInfo
import com.backinfile.cardRouge.viewGroups.ButtonsParam
import javafx.beans.property.SimpleIntegerProperty


object CardPlayLogic {
    private var requestTurnOver: Boolean = false

    suspend fun enablePlayCardInHand(context: Context) {
        Log.game.info("enablePlayCardInHand")
        if (context.human !is Player) return
        context.human.handPile.forEach { calcCardPlayableState(context, it) }
        BoardHandPileGroup.enablePlay(true, context)

        requestTurnOver = false
        BoardButtonsUIGroup.show(ButtonsParam(ButtonInfo(Res.TEXT_TURN_END) {
            requestTurnOver = true
            playerOperationFinish()
        }))

        // 等待玩家操作
        context.board.waitCondition(playerOperation) { true }

        if (requestTurnOver) {
            context.board.changeBoardStateTo(Board.State.TurnAfter)
        }
    }

    /**
     * @return true if played
     */
    suspend fun handleDragPlayEnd(context: Context, card: Card, curSlotIndex: Int = -1): Boolean {
        disablePlayerCardInHand(context)
        val selectResult = context.selectCardTarget(card, curSlotIndex)
        if (!selectResult.ok) {
            context.refreshHandPileView()
            enablePlayCardInHand(context)
            return false

        }
        // 成功打出
        playCard(context, card, selectResult)

        // 一个操作完成
        playerOperationFinish()
        return true
    }

    private fun disablePlayerCardInHand(context: Context) {
        Log.game.info("disablePlayerCardInHand")
        BoardHandPileGroup.enablePlay(false, context)
        BoardButtonsUIGroup.hide()
    }


    private fun calcCardPlayableState(context: Context, card: Card): CardPlayTargetInfo? {
        card.playTargetInfo = null
        card.calcCost()
        if (context.human is Player) {
            if (context.human.mana < card.manaCost) return null
        }


        if (card.confCard.cardType == GameConfig.CARD_TYPE_UNIT) {
            return CardPlayTargetInfo(playMinion = true).also { card.playTargetInfo = it }
        }
        return null
    }


    private suspend fun playCard(context: Context, card: Card, target: ViewActions.SelectTargetResult) = with(context) {
        if (human !is Player) return
        human.handPile.remove(card)

        // cost mana
        human.mana = maxOf(0, human.mana - card.manaCost)




        // card effect
        when (card.confCard.cardType) {
            GameConfig.CARD_TYPE_UNIT -> {
                val targetSlotIndex = target.targetSlotIndex;
                summonTo(targetSlotIndex, card)
                // 触发打出效果 TODO
            }

            GameConfig.CARD_TYPE_ACTION -> {
                // 触发打出效果 TODO
                moveCardToDiscardPile(card)
            }

            else -> {
                TODO()
            }
        }


        refreshHandPileView()
    }


    /**
     * 等待玩家执行操作， 打出卡牌，或回合结束
     */
    private val playerOperation = SimpleIntegerProperty(0)
    private fun playerOperationFinish() {
        playerOperation.set(playerOperation.get() + 1)
    }
}