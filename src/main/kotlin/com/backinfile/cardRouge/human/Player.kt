package com.backinfile.cardRouge.human

import com.backinfile.cardRouge.card.CardPile

class Player : HumanBase() {
    val handPile: CardPile = CardPile()
    val drawPile: CardPile = CardPile()
    val discardPile: CardPile = CardPile()
    val trashPile: CardPile = CardPile()

    override val allCardPiles: List<CardPile>
        get() = listOf(handPile, drawPile, discardPile, trashPile) + super.allCardPiles

    override fun isPlayer(): Boolean {
        return true
    }

    override fun init() {

//        for (card in dungeonData.deck) {
//            drawPile.addCard(CardFactory.createCardInstance(card.id, isPlayer()))
//            // TODO 强化
//        }
//        drawPile.shuffle(dungeon.getCardRandom())
//
//        for (id in dungeonData.powers) {
//            powerPile.addCard(CardFactory.createCardInstance(id, isPlayer()))
//        }
    }

    override suspend fun playInTurn() {
        super.playInTurn()
    }
}