package com.backinfile.cardRouge.human

import com.backinfile.cardRouge.action.Actions.changeBoardStateTo
import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardPile
import com.backinfile.cardRouge.human.enemy.EnemyBase

class Robot : HumanBase() {

    lateinit var mainMonster: Card;
    val wanderPile: CardPile = CardPile()

    override fun isPlayer() = false

    override suspend fun preBattleStart() {
        super.preBattleStart()

        // create boss

        for (slot in slots.values) {
            val minion = slot.minion
            if (minion is EnemyBase && !minion.leader) {
                minion.nextActionPreview(context)
            }
        }
        for (slot in slots.values) {
            val minion = slot.minion
            if (minion is EnemyBase && minion.leader) {
                minion.nextActionPreview(context)
            }
        }
    }


    override suspend fun playInTurn() {
        for (slot in slots.values) {
            val minion = slot.minion
            if (minion is EnemyBase && !minion.leader) {
                minion.doAction(context)
                minion.nextActionPreview(context)
            }
        }
        for (slot in slots.values) {
            val minion = slot.minion
            if (minion is EnemyBase && minion.leader) {
                minion.doAction(context)
                minion.nextActionPreview(context)
            }
        }
        board.changeBoardStateTo(Board.State.TurnAfter)
    }

}