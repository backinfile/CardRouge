package com.backinfile.cardRouge.action

typealias GameAction = suspend ActionContext.() -> Unit
typealias GameActionRun = suspend () -> Unit

object Actions {
    suspend fun ActionContext.drawCard(number: Number) {
        shuffleDrawPile()

    }

    suspend fun ActionContext.shuffleDrawPile() {

    }
}