package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.buff.BuffContainer
import com.backinfile.cardRouge.buffs.ModifyManaCostBuff

open class Card : BuffContainer() {
    open val confCard: CardConfig get() = CardConfig.EMPTY

    var manaCost: Int = 0
        private set
    var fromPlayer: Boolean = false
    var playTargetInfo: CardPlayTargetInfo? = null

    val description: String = ""

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


open class CardConfig(
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
) {
    companion object {
        val EMPTY: CardConfig = CardConfig()
    }

}