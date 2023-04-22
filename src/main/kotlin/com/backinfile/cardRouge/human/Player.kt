package com.backinfile.cardRouge.human

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.action.Actions.changeBoardStateTo
import com.backinfile.cardRouge.action.OperationActions.selectCardFrom
import com.backinfile.cardRouge.action.OperationActions.waitPressTurnEnd
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardPile
import com.backinfile.cardRouge.gen.config.ConfCard
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup

class Player : HumanBase() {
    var manaMax: Int = GameConfig.MANA_MAX_DEFAULT // 费用上限
    var handMax: Int = GameConfig.HAND_SIZE_DEFAULT_MAX // 手牌上限
    var mana = 0 // 费用

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
        repeat(10) { drawPile.addCard(Card(ConfCard.get(1201001))) }

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

    override suspend fun playInTurn() = with(context) {
//        val selected = selectCardFrom(handPile.toList(), 1, false).first()
//        Log.game.info("已选择 {}", selected.confCard.title)
        BoardHandPileGroup.enablePlay(true)
        waitPressTurnEnd()
        board.changeBoardStateTo(Board.State.TurnAfter)
    }
}