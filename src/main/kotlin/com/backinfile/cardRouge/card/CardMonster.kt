package com.backinfile.cardRouge.card

open class CardMonster : Card() {

    private val actionQueue = ArrayList<suspend () -> Unit>()
    private var curActionIndex = 0;

    open suspend fun nextAction() {
        if (curActionIndex !in actionQueue.indices) return

        val curAction = actionQueue[curActionIndex]
        curAction.invoke()

        curActionIndex = (curActionIndex + 1) % actionQueue.size
    }

}