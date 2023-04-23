package com.backinfile.cardRouge.human

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.action.Actions.drawCard
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.buff.BuffContainer
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardPile
import com.backinfile.cardRouge.card.CardSlot

abstract class HumanBase : BuffContainer() {

    val powerPile: CardPile = CardPile() // 能力牌

    protected open val allCardPiles: List<CardPile> = listOf(powerPile)
    val slots: MutableMap<Int, CardSlot> = mutableMapOf()

    val context: Context by lazy { Context(dungeon, board, this) }

    abstract fun isPlayer(): Boolean

    open fun init() {
    }

    open suspend fun playInTurn() {

    }

    open suspend fun onBattleStart() {}


    open suspend fun onBattleEnd() {

    }

    open suspend fun onTurnStart() {

    }


    fun removeCard(card: Card) {
        for (pile in allCardPiles) {
            pile.remove(card)
        }
        for (slot in slots.values) {
            if (slot.minion == card) {
                slot.minion = null
            }
            if (slot.seal == card) {
                slot.seal = null
            }
        }
    }

    fun hasEmptySlot(toSummon: Boolean = false): Boolean {
        return slots.values.any { it.isEmpty(toSummon) }
    }

    fun getFirstEmptySlotIndex(toSummon: Boolean = false): Int {
        for ((index, slot) in slots.entries) {
            if (slot.isEmpty(toSummon)) {
                return index
            }
        }
        return -1
    }

    fun getSlotIndex(card: Card): Int {
        for ((index, slot) in slots.entries) {
            if (slot.minion == card || slot.seal == card) {
                return index
            }
        }
        return -1
    }

    fun findMinions(predicate: (Card) -> Boolean): List<Card> {
        return slots.values.mapNotNull { it.minion }.filter { predicate(it) }
    }

    fun getAllCards(): List<Card> {
        return allCardPiles.flatten() + slots.values.flatMap { it.getAllCards() }
    }
}