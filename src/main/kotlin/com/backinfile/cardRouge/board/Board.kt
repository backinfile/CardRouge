package com.backinfile.cardRouge.board

import com.almasb.fxgl.core.Updatable
import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.action.Actions.changeBoardStateTo
import com.backinfile.cardRouge.action.GameActionQueue
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.HumanBase
import com.backinfile.cardRouge.human.Player
import com.backinfile.cardRouge.human.Robot
import com.backinfile.cardRouge.viewGroups.BoardHandPileGroup
import com.backinfile.cardRouge.viewGroups.SlotViewUtils
import com.backinfile.support.async.runAsync
import com.backinfile.support.kotlin.Action1
import com.backinfile.support.kotlin.TimerQueue
import com.backinfile.support.kotlin.once
import java.io.Closeable
import java.util.concurrent.ConcurrentHashMap
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

    private var curTime = 0.0
    val timerQueue = TimerQueue { curTime }
    private val updaterContainer = ConcurrentHashMap<Updatable, Unit>()

    // 正在执行异步函数，暂停除特效外的全部操作
    private val asyncLocks = ConcurrentHashMap<Closeable, Unit>()

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
            human.init()
        }
        startHuman = player
        turnCurHuman = player
    }


    override fun onUpdate(delta: Double) {
        curTime += delta

        // 常驻更新
        timerQueue.update()
        effectActionQueue.update(delta)
        updaterContainer.forEach { it.key.onUpdate(delta) }


        if (state == State.None) {
            enterState(State.Prepare)
            return
        }


        // 更新行动队列
        while (asyncLocks.isNotEmpty() && !actionQueue.isEmpty()) {
            actionQueue.update(delta)
        }
        if (asyncLocks.isNotEmpty()) {
            return
        }

        if (state == State.Turn) {
            // 当前玩家执行动作
            runAsync {
                turnCurHuman.playInTurn()
            }
        }
    }

    fun enterState(state: State) = runAsync {
        val board = this@Board
        val oldState = board.state.also { board.state = state }

        Log.board.info("change board state [{}] to [{}]", oldState, state)

        when (state) {
            State.None -> TODO()
            State.Prepare -> {
                BoardHandPileGroup.hide()
                BoardHandPileGroup.show()
                for (human in humans) {
                    SlotViewUtils.initSlotPosition(human.slots, human.isPlayer())
                    if (human.isPlayer()) SlotViewUtils.createSlotsView(human.slots, human.isPlayer())
                }
                for (human in humans) {
                    human.onBattleStart()
                }
                changeBoardStateTo(State.TurnBefore)
            }

            State.TurnBefore -> {
                changeBoardStateTo(State.Turn)
            }

            State.Turn -> {}
            State.TurnAfter -> {


                bigTurnCount++
                val curIndex = humans.indexOf(turnCurHuman)
                turnCurHuman = humans[(curIndex + 1) % humans.size]
                Log.board.info("change cur player to {}", turnCurHuman::class.simpleName)

                changeBoardStateTo(State.TurnBefore)
            }

            State.OVER -> TODO()
            State.OVER_CLEAR -> TODO()
        }
    }


    fun getPlayer() = humans.filterIsInstance<Player>().first()

    fun addUpdater(updater: Updatable) {
        updater.setBoard(this)
        updaterContainer[updater] = Unit
    }

    abstract class Updatable : com.almasb.fxgl.core.Updatable {
        private lateinit var board: Board
        fun destroy() {
            board.updaterContainer.remove(this)
        }

        fun setBoard(board: Board) {
            this.board = board
        }
    }

    fun getAsyncLock(): Closeable {
        val closeable = object : Closeable {
            override fun close() {
                asyncLocks.remove(this)
            }
        }
        asyncLocks[closeable] = Unit
        return closeable
    }

    fun removeCard(card: Card) {
        humans.forEach { it.removeCard(card) }
    }
}