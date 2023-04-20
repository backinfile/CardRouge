package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.action.ViewActions.refreshHandCardAction
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.HumanBase
import com.backinfile.cardRouge.human.Player
import com.backinfile.support.async.runAsync
import com.backinfile.support.kotlin.once
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.properties.Delegates


typealias GameAction = suspend ActionContext.() -> Unit
typealias GameActionRun = suspend () -> Unit

fun ActionContext.wrap(gameAction: GameAction): GameActionRun = { gameAction.invoke(this) }

data class ActionContext(val dungeon: Dungeon, val board: Board, val human: HumanBase) {

    suspend fun shuffleDiscardIntoDrawPile() {
        if (human !is Player) return
        human.drawPile.addCards(human.discardPile)
        human.discardPile.clear()
        human.drawPile.shuffle(dungeon.cardRandom)
    }

    suspend fun drawCard(number: Int = 1) {
        for (i in 0 until number) drawOneCard()
    }

    private suspend fun drawOneCard() {
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


    suspend fun selectCardFrom(cards: List<Card>, cnt: Int = 1, optional: Boolean = false): List<Card> = suspendCoroutine { continuation ->
        board.inAsyncEvent = true

        // 异步执行
        runAsync {
            val selected = ArrayList<Card>()
            for (card in cards) {
                val cardView = CardViewManager.getOrCreate(card)
                cardView.modInteract.disableAll()
                cardView.modInteract.enableClick {
                    if (it.card in selected) selected.remove(it.card) else selected.add(it.card)
                }
                cardView.modInteract.setDark(false)
            }

            val confirmedSelected = ArrayList<Card>()

            board.waitCondition { confirmedSelected.size == cnt } // 等待满足条件

            board.inAsyncEvent = false
            continuation.resume(selected) // 继续执行当前行动
        }
    }
}