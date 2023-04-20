package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.HumanBase


typealias GameAction = suspend Context.() -> Unit
typealias GameActionRun = suspend () -> Unit


/**
 * @param human 执行该操作的玩家
 */
data class Context(val dungeon: Dungeon, val board: Board, val human: HumanBase) {

    fun wrap(gameAction: GameAction): GameActionRun = { gameAction.invoke(this) }
}