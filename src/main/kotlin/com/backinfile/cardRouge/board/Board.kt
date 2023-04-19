package com.backinfile.cardRouge.board

import com.almasb.fxgl.core.Updatable
import com.backinfile.cardRouge.action.GameActionQueue
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.HumanBase
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.human.Robot
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup
import com.backinfile.support.async.runAsync
import com.backinfile.support.func.Action1
import com.backinfile.support.kotlin.once
import kotlin.properties.Delegates

class Board : Updatable {
    var dungeon: Dungeon by Delegates.once()

    private var startHuman: HumanBase by Delegates.once() // 本次对战先手玩家

    val humans = ArrayList<HumanBase>() // 对战双方
    private lateinit var turnCurHuman: HumanBase // 当前玩家

    private var bigTurnCount = 1 // 大回合数

    private var state = State.None
    private var winner: HumanBase? = null // 获胜者

    private var gameOverListener: Action1<HumanBase>? = null

    // 逻辑action队列，基于现实时间依次执行
    private val actionQueue: GameActionQueue = GameActionQueue()

    // 特效action队列，基于现实时间同时执行
    private val effectActionQueue: GameActionQueue = GameActionQueue(true)

    var inAsyncEvent = false // 正在执行异步函数，暂停除特效外的全部操作

    enum class State {
        None,
        Prepare,
        TurnBefore,
        Turn,
        TurnAfter,
        OVER,
        OVER_CLEAR
    }

    fun init(player: Player, robot: Robot) {
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
        turnCurHuman = player
    }


    override fun onUpdate(delta: Double) {

        if (state == State.None) {
            enterState(State.Prepare)
            return
        }


        // 更新特效队列
        effectActionQueue.update(delta)


        // 更新行动队列
        while (!inAsyncEvent && !actionQueue.isEmpty()) {
            actionQueue.update(delta)
        }
        if (inAsyncEvent) {
            return
        }


        // 当前玩家执行动作
        runAsync {
            turnCurHuman.playInTurn()
        }
    }

    fun enterState(state: State) {
        val oldState = this.state.also { this.state = state };
        when (state) {
            State.None -> TODO()
            State.Prepare -> {

                BoardHandPileGroup.hide()
                BoardHandPileGroup.show()


                runAsync {
                    humans.forEach { it.onBattleStart() }
                }
            }

            State.TurnBefore -> {

            }

            State.Turn -> TODO()
            State.TurnAfter -> {
                bigTurnCount++
            }

            State.OVER -> TODO()
            State.OVER_CLEAR -> TODO()
        }

    }


    fun getPlayer() = humans.filterIsInstance<Player>().first()
}