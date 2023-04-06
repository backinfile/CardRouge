package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.buff.BuffContainer
import com.backinfile.cardRouge.buffs.ModifyManaCostBuff
import com.backinfile.cardRouge.gen.config.ConfCard

abstract class Card(private val confCard: ConfCard, val oriPlayerCard: Boolean = true) : BuffContainer() {
    private var manaCost = 0
    private val counter = -1

    fun getConf() = confCard


    fun calcCost() {
        var modify = 0
        for (buff in getAllBuffs()) {
            if (buff is ModifyManaCostBuff) {
                modify += buff.value
            }
        }
        manaCost = maxOf(0, confCard.cost - modify)
    }
}