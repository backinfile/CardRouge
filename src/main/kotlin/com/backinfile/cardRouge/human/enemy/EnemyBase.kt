package com.backinfile.cardRouge.human.enemy

import com.backinfile.cardRouge.action.Actions.damage
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.card.Card

abstract class EnemyBase(var healthMax: Int = 1) : Card() {
    var health = healthMax

    abstract suspend fun actionPreview(context: Context): EnemyActionPreview

    open suspend fun doAction(context: Context, actionPreview: EnemyActionPreview) {
        commonDoTips(context, actionPreview)
        commonDoAttack(context, actionPreview)
    }


    protected suspend fun commonDoAttack(context: Context, actionPreview: EnemyActionPreview): Boolean {
        val attackInfo = actionPreview.attack ?: return false

        when (attackInfo.attackType) {
            AttackAction.AttackType.Pure -> context.damage(attackInfo.targetSlotIndex)
        }
        return true
    }

    protected suspend fun commonDoTips(context: Context, actionPreview: EnemyActionPreview) {
        TODO()
    }
}