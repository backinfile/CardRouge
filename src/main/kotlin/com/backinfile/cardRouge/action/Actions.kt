package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.action.ViewActions.attackView
import com.backinfile.cardRouge.action.ViewActions.moveCardToSlot
import com.backinfile.cardRouge.action.ViewActions.refreshHandPileView
import com.backinfile.cardRouge.action.ViewActions.updatePileNumber
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.human.HumanBase
import com.backinfile.cardRouge.human.Player

object Actions {

    suspend fun Board.changeBoardStateTo(state: Board.State) {
        enterState(state)
    }

    suspend fun Context.attack(card: Card, targetSlotIndex: Int) {

        // 先移除卡牌
        board.removeCard(card)

        // 播放动画
        attackView(card, targetSlotIndex)

        val targetSlot = human.opponent.slots[targetSlotIndex]!!

        // 槽位没有单位，直接击破
        if (targetSlot.minion == null) {
            targetSlot.seal = true
        }
    }


    suspend fun Context.resetMana() {
        if (human is Player) {
            human.mana = human.manaMax
            // TODO view
        }
    }

    suspend fun Context.summonTo(slotIndex: Int, card: Card) {
        board.removeCard(card)
        val slot = human.slots[slotIndex]!!
        if (slot.minion != null) {
            replace(slotIndex, card)
            return
        } else {
            slot.minion = card
        }

        moveCardToSlot(slotIndex, card)
        refreshHandPileView()
    }

    suspend fun Context.replace(slotIndex: Int, card: Card) {
        TODO()
    }

    suspend fun Context.shuffleDiscardIntoDrawPile() {
        if (human !is Player) return
        human.drawPile.addCards(human.discardPile)
        human.discardPile.clear()
        human.drawPile.shuffle(dungeon.cardRandom)
    }

    suspend fun Context.moveCardToDiscardPile(card: Card) {
        board.removeCard(card)
        if (human is Player) {
            human.discardPile.addCard(card)
        }
        updatePileNumber()
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

        refreshHandPileView()
    }

}