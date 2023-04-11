package com.backinfile.cardRouge.board

import com.backinfile.cardRouge.action.GameActionQueue
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.Human
import com.backinfile.cardRouge.human.Player
import com.backinfile.support.func.Action1

class Board {
    lateinit var dungeon: Dungeon

    private lateinit var startHuman: Human // 本次对战先手玩家

    private val humans = ArrayList<Human>() // 对战双方
    private val turnCurHuman: Human? = null // 当前玩家

    private val bigTurnCount = 1 // 大回合数

    private val state = State.None
    private var winner: Human? = null // 获胜者

    private val gameOverListener: Action1<Human>? = null

    // 逻辑action队列，基于现实时间依次执行
    private val actionQueue: GameActionQueue = GameActionQueue()

    // 特效action队列，基于现实时间同时执行
    private val effectActionQueue: GameActionQueue = GameActionQueue(true)


    enum class State {
        None,
        Prepare,
        TurnBefore,
        Turn,
        TurnAfter,
        OVER,
        OVER_CLEAR
    }

    fun init(player: Player, robot: Human) {
        humans.add(player)
        humans.add(robot)
        for (human in humans) {
            human.dungeon = dungeon
            human.board = this
            human.human = human
            for (card in human.getAllCards()) {
                card.dungeon = dungeon
                card.board = this
                card.human = human
            }
        }
        startHuman = player
    }

}