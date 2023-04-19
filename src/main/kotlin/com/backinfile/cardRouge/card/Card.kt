package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.buff.BuffContainer
import com.backinfile.cardRouge.buffs.ModifyManaCostBuff
import com.backinfile.cardRouge.gen.config.ConfCard

open class Card(val confCard: ConfCard, val oriPlayerCard: Boolean = true) : BuffContainer() {
    private var manaCost = 0

    val id get() = confCard.id


    fun calcCost(): Int {
        var modify = 0
        for (buff in getAllBuffs()) {
            if (buff is ModifyManaCostBuff) {
                modify += buff.value
            }
        }
        manaCost = maxOf(0, confCard.cost - modify)
        return manaCost
    }
}