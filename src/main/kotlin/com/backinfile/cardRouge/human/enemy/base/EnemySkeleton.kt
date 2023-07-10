package com.backinfile.cardRouge.human.enemy.base

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.card.CardConfig
import com.backinfile.cardRouge.human.enemy.AttackAction
import com.backinfile.cardRouge.human.enemy.EnemyActionPreview
import com.backinfile.cardRouge.human.enemy.EnemyBase

class EnemySkeleton : EnemyBase() {
    override val confCard: CardConfig
        get() = CardConfig(
            id = this::class.simpleName!!,
            title = "Skeleton",
            family = GameConfig.FAMILY_ELEMENT,
            cardType = GameConfig.CARD_TYPE_UNIT,
            subType = "",
            rare = GameConfig.RARE_INIT,
            cost = 1,
            description = "",
            image = "card/fire.png"
        )


    override suspend fun nextAction(context: Context): EnemyActionPreview {
        val slotIndex = context.human.getSlotIndex(this)
        return if (slotIndex >= 0) {
            EnemyActionPreview(0, attack = AttackAction(targetSlotIndex = slotIndex))
        } else {
            EnemyActionPreview(-1)
        }
    }
}