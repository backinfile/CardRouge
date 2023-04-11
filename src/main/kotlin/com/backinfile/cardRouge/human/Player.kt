package com.backinfile.cardRouge.human

class Player : Human() {
    override fun isPlayer(): Boolean {
        return true
    }

    override fun playInTurn() {
    }

    override fun initPower() {
    }

    override fun initPiles() {

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
}