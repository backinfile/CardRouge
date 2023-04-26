package com.backinfile.cardRouge.card

import com.backinfile.support.kotlin.Random
import java.util.Collections
import java.util.function.Predicate
import kotlin.collections.ArrayList

class CardPile : Iterable<Card> {
    // 牌库底->牌库顶
    private val cardList: MutableList<Card> = ArrayList()

    constructor()
    constructor(card: Card) {
        cardList.add(card)
    }

    constructor(vararg cards: Card) {
        cardList.addAll(cards)
    }

    constructor(cards: Collection<Card>?) {
        cardList.addAll(cards!!)
    }


    fun addCard(card: Card) {
        cardList.add(card)
    }

    fun addCards(cards: Collection<Card>?) {
        cardList.addAll(cards!!)
    }

    fun addCards(cardPile: CardPile) {
        cardList.addAll(cardPile.cardList)
    }

    fun drawCard(): Card? {
        return if (cardList.isNotEmpty()) cardList.removeLast()
        else null
    }

    fun drawCard(n: Int): List<Card> {
        if (cardList.isEmpty() || n <= 0) {
            return listOf()
        }
        val result: MutableList<Card> = ArrayList()
        var i = 0
        while (i < n && cardList.isNotEmpty()) {
            result.add(cardList.removeLast())
            i++
        }
        return Collections.unmodifiableList(result)
    }

    fun size(): Int {
        return cardList.size
    }

    fun shuffle(random: Random) {
        for (i in cardList.indices) {
            val rng = random.next(cardList.size)
            if (i != rng) {
                Collections.swap(cardList, i, rng)
            }
        }
    }

    operator fun get(i: Int): Card {
        return cardList[i]
    }

    fun remove(card: Card) {
        cardList.remove(card)
    }

    val isEmpty: Boolean
        get() = cardList.isEmpty()

    operator fun contains(card: Card): Boolean {
        return cardList.contains(card)
    }

    fun clear() {
        cardList.clear()
    }

    fun toList(): List<Card> {
        return ArrayList(cardList)
    }

    fun toUnmodifiableList(): List<Card> {
        return java.util.List.copyOf(cardList)
    }

    fun findCards(predicate: Predicate<Card>): List<Card> {
        val result = ArrayList<Card>()
        for (card in cardList) {
            if (predicate.test(card)) {
                result.add(card)
            }
        }
        return result
    }

    fun findAnyCard(predicate: Predicate<Card>): Card? {
        for (card in cardList) {
            if (predicate.test(card)) {
                return card
            }
        }
        return null
    }

    override fun iterator(): Iterator<Card> {
        return cardList.iterator()
    }
}
