package com.backinfile.cardRouge.human

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.action.Actions.changeBoardStateTo
import com.backinfile.cardRouge.action.Actions.drawCard
import com.backinfile.cardRouge.action.OperationActions.waitPressTurnEnd
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.CardPile
import com.backinfile.cardRouge.card.CardPlayLogic
import com.backinfile.cardRouge.card.action.CardAttack
import com.backinfile.cardRouge.card.element.CardFire
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup

class Player : HumanBase() {
    var manaMax: Int = GameConfig.MANA_MAX_DEFAULT // 费用上限
    var handMax: Int = GameConfig.HAND_SIZE_DEFAULT_MAX // 手牌上限
    var mana = 0 // 费用

    val handPile: CardPile = CardPile()
    val drawPile: CardPile = CardPile()
    val discardPile: CardPile = CardPile()
    val trashPile: CardPile = CardPile()
    override val allCardPiles: List<CardPile> = listOf(handPile, drawPile, discardPile, trashPile) + super.allCardPiles

    override fun isPlayer(): Boolean {
        return true
    }

    override fun init() {
        super.init()


        repeat(10) {
            drawPile.addCard(CardFire())
            drawPile.addCard(CardAttack())
        }

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
        handPile.forEach { it.calcCost() }
        handPile.forEach { CardPlayLogic.calcCardPlayableState(context, it) }
        BoardHandPileGroup.enablePlay(true, context)
        waitPressTurnEnd()
        BoardHandPileGroup.enablePlay(false)
        board.changeBoardStateTo(Board.State.TurnAfter)
    }

    override suspend fun onBattleStart() = with(context) {
        super.onBattleStart()
        drawCard(5)
    }
}