package com.backinfile.cardRouge.action

import com.backinfile.support.async.runAsync

class GameActionQueue(private val immediately: Boolean = false) {
    private val queue = ArrayDeque<GameActionRun>()
    var running = false
        private set

    fun addTop(gameAction: GameActionRun) {
        if (immediately) {
            runAsync { gameAction() }
            return
        }
        queue.addLast(gameAction)
    }

    fun addBottom(gameAction: GameActionRun) {
        if (immediately) {
            runAsync { gameAction() }
            return
        }
        queue.addFirst(gameAction)
    }

    fun clear() {
        queue.clear()
    }

    fun update(delta: Double) {
        if (running) return

        if (queue.isNotEmpty()) {
            val action = queue.removeFirst()
            running = true
            runAsync {
                action()
                running = false
            }
        }
    }

    fun isEmpty(): Boolean {
        return !running && queue.isEmpty()
    }
}