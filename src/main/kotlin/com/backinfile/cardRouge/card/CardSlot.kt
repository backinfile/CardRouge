package com.backinfile.cardRouge.card

import com.almasb.fxgl.core.math.Vec2
import com.backinfile.cardRouge.buff.BuffContainer

class CardSlot : BuffContainer() {
    var minion: Card? = null
    var seal: Card? = null


    var position: Vec2 = Vec2();
    lateinit var crystal: Card

    fun isEmpty(toSummon: Boolean = false): Boolean {
        return minion == null && seal == null
    }

    fun getAllCards(): List<Card> {
        return listOfNotNull(minion, seal)
    }
}