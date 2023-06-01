package com.backinfile.cardRouge.card

import com.almasb.fxgl.core.math.Vec2
import com.backinfile.cardRouge.buff.BuffContainer

class CardSlot(val index: Int) : BuffContainer() {
    var minion: Card? = null
    var seal: Boolean = false


    var position: Vec2 = Vec2();
    lateinit var crystal: Card

    fun isEmpty(toSummon: Boolean = false): Boolean {
        return minion == null && !seal
    }

    fun getAllCards(): List<Card> {
        return listOfNotNull(minion)
    }
}