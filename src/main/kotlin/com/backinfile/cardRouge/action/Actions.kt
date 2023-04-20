package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.action.ViewActions.refreshHandCardAction
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.viewGroups.BoardButtonsUIGroup
import com.backinfile.cardRouge.viewGroups.ButtonInfo
import com.backinfile.cardRouge.viewGroups.ButtonsParam
import com.backinfile.support.async.runAsync
import javafx.beans.property.SimpleBooleanProperty
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object Actions {

    suspend fun Board.changeBoardStateTo(state: Board.State) {
        enterState(state)
    }

    suspend fun Context.shuffleDiscardIntoDrawPile() {
        if (human !is Player) return
        human.drawPile.addCards(human.discardPile)
        human.discardPile.clear()
        human.drawPile.shuffle(dungeon.cardRandom)
    }

    suspend fun Context.drawCard(number: Int = 1) {
        for (i in 0 until number) drawOneCard()
    }

    private suspend fun Context.drawOneCard() {
        if (human !is Player) return

        if (human.drawPile.isEmpty) {
            shuffleDiscardIntoDrawPile()
        }
        if (human.drawPile.isEmpty) return // TODO message

        val card = human.drawPile.drawCard()!!
        human.handPile.addCard(card)
        Log.game.info("player draw 1 card {}", card.confCard.title)

        refreshHandCardAction()
    }

    suspend fun Context.waitPressTurnEnd() {
        val confirmProperty = SimpleBooleanProperty(false)
        BoardButtonsUIGroup.show(ButtonsParam(ButtonInfo(Res.TEXT_TURN_END) {
            confirmProperty.set(true)
        }))

        board.waitCondition(confirmProperty) { it }

        BoardButtonsUIGroup.hide()
    }


    suspend fun Context.selectCardFrom(cards: List<Card>, cnt: Int = 1, optional: Boolean = false): List<Card> {
        val confirmProperty = SimpleBooleanProperty(false)
        val selected = ArrayList<Card>()

        BoardButtonsUIGroup.show()
        BoardButtonsUIGroup.setButton(0, Res.TEXT_CONFIRM)
        BoardButtonsUIGroup.setButton(1, Res.TEXT_CANCEL) { confirmProperty.set(true) }

        for (card in cards) {
            val cardView = CardViewManager.getOrCreate(card)
            cardView.modInteract.disableAll()
            cardView.modInteract.enableClick {
                if (it.card in selected) {
                    selected.remove(it.card)
                } else {
                    selected.add(it.card)
                }

                if (selected.size == cnt) {
                    BoardButtonsUIGroup.setButton(0, Res.TEXT_CONFIRM) { confirmProperty.set(true) }
                } else {
                    BoardButtonsUIGroup.setButton(0, Res.TEXT_CONFIRM)
                }
            }
            cardView.modInteract.setDark(false)
        }


        board.waitCondition(confirmProperty) { it } // 等待满足条件

        // 清理UI
        BoardButtonsUIGroup.hide()

        for (card in cards) {
            val cardView = CardViewManager.getOrCreate(card)
            cardView.modInteract.disableAll()
        }

        return selected
    }
}