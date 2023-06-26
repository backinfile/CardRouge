package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.action.Actions.discard
import com.backinfile.cardRouge.action.ViewActions.moveCardToDiscardPile
import com.backinfile.cardRouge.action.ViewActions.moveCardToSlot
import com.backinfile.cardRouge.action.ViewActions.refreshHandPileView
import com.backinfile.cardRouge.action.ViewActions.updatePileNumber
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.human.HumanBase
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.human.enemy.EnemyBase

object Actions {

    suspend fun Board.changeBoardStateTo(state: Board.State) {
        enterState(state)
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
        if (human !is Player) {
            Log.action.warn("robot can not draw card")
            return
        }

        if (human.drawPile.isEmpty) {
            shuffleDiscardIntoDrawPile()
        }
        if (human.drawPile.isEmpty) return // TODO message

        val card = human.drawPile.drawCard()!!
        human.handPile.addCard(card)
        Log.game.info("player draw 1 card {}", card.confCard.title)

        refreshHandPileView()
    }

    suspend fun Context.attack(attackCard: Card, targetSlotIndex: Int, heavyAttack: Boolean = true) {
        TODO()
    }

    suspend fun Context.damage(targetSlotIndex: Int, num: Int = 1, heavyAttack: Boolean = true) {
        for (i in 1..num) {
            val targetSlot = opponent.slots[targetSlotIndex]!!
            if (targetSlot.seal) continue

            val targetMinion = targetSlot.minion
            if (targetMinion != null) {
                loseHealth(targetMinion, 1)
            } else if (heavyAttack) {
                seal(opponent, targetSlotIndex)
            }
        }
    }

    private suspend fun Context.loseHealth(card: Card, num: Int = 1) {
        if (card is EnemyBase) {
            if (card.health <= 1) {
                discard(card)
            } else {
                card.health--
            }
        } else {
            discard(card)
        }
    }

    suspend fun Context.discard(card: Card) {
        board.removeCard(card)
        if (human is Player) {
            human.discardPile.addCard(card)
            moveCardToDiscardPile(card)
        }
    }

    suspend fun Context.seal(targetHuman: HumanBase, index: Int) {
        val cardSlot = targetHuman.slots[index]!!
        cardSlot.seal = true
        TODO("view")
    }
}