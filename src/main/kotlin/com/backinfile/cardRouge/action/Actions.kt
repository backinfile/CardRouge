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

}