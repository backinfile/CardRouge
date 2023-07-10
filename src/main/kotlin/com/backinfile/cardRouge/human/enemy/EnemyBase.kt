package com.backinfile.cardRouge.human.enemy

import com.backinfile.cardRouge.action.Actions.damage
import com.backinfile.cardRouge.action.Context
import com.backinfile.cardRouge.card.Card

abstract class EnemyBase(var healthMax: Int = 1, val leader: Boolean = false) : Card() {
    var health = healthMax

    protected var curActionPreview: EnemyActionPreview? = null;

    open suspend fun nextActionPreview(context: Context) {
        this.curActionPreview = nextAction(context)
    }

    open suspend fun doAction(context: Context) {
        val actionPreview = curActionPreview ?: return
        commonDoTips(context, actionPreview)
        commonDoAttack(context, actionPreview)
    }


    protected open suspend fun nextAction(context: Context): EnemyActionPreview {
        return EnemyActionPreview(description = "IDLE", tip = "IDLE")
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