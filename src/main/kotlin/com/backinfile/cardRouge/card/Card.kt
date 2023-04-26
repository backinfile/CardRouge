package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.buff.BuffContainer
import com.backinfile.cardRouge.buffs.ModifyManaCostBuff
import com.backinfile.cardRouge.gen.config.ConfCard
import com.backinfile.support.kotlin.once
import kotlin.properties.Delegates

open class Card : BuffContainer() {
    open val confCard: CardConfig = CardConfig()

    private var manaCost = 0
    var fromPlayer: Boolean = false
    var playTargetInfo: CardPlayTargetInfo? = null


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

data class CardConfig(
    val id: String = "[ID]",
    val title: String = "[TITLE]",
    val family: Int = GameConfig.FAMILY_COMMON,
    val cardType: Int = GameConfig.CARD_TYPE_ACTION,
    val subType: String = "",
    val rare: Int = GameConfig.RARE_COMMON,
    val cost: Int = 0,
    val health: Int = 1,
    val description: String = "",
    val image: String = "",
    val backImage: String = "",
)