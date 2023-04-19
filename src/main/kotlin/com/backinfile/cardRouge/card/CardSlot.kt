package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.buff.BuffContainer
import javafx.geometry.Point2D
import kotlin.math.min

class CardSlot : BuffContainer() {
    var minion: Card? = null
    var seal: Card? = null
    var position: Point2D? = null

    fun isEmpty(toSummon: Boolean = false): Boolean {
        return minion == null && seal == null
    }

    fun getAllCards(): List<Card> {
        return listOfNotNull(minion, seal)
    }
}