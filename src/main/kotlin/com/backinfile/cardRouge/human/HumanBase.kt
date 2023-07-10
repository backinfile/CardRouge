package com.backinfile.cardRouge.human

import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.buff.BuffContainer
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardPile
import com.backinfile.cardRouge.card.CardSlot
import com.backinfile.cardRouge.viewGroups.SlotViewUtils

abstract class HumanBase : BuffContainer() {

    val slots: Map<Int, CardSlot> = (0 until 5).associateWith { CardSlot(it) }
    val powerPile: CardPile = CardPile() // 能力牌

    protected open val allCardPiles: List<CardPile> = listOf(powerPile)

    val context: Context by lazy { Context(dungeon, board, this) }
    val opponent: HumanBase by lazy { board.humans.first { it != this } }

    abstract fun isPlayer(): Boolean

    open fun init() {

    }

    open suspend fun playInTurn() {

    }

    open suspend fun preBattleStart() {
    }
    open suspend fun onBattleStart() {
    }


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
            if (slot.seal) {
                slot.seal = false
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
            if (slot.minion == card || slot.seal) {
                return index
            }
        }
        return -1
    }

    fun findMinions(predicate: (Card) -> Boolean): List<Card> {
        return slots.values.mapNotNull { it.minion }.filter { predicate(it) }
    }

    open fun getAllVisibleCards(): List<Card> {
        return slots.values.mapNotNull { it.minion }
    }

    fun getAllCards(): List<Card> {
        return allCardPiles.flatten() + slots.values.flatMap { it.getAllCards() }
    }

    fun getCrystalSlotIndex(crystalCard: Card): Int {
        for ((index, slot) in slots.entries) {
            if (slot.crystal == crystalCard) {
                return index
            }
        }
        return -1
    }
}