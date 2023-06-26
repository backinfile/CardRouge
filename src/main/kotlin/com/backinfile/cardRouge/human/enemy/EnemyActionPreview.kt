package com.backinfile.cardRouge.human.enemy

import com.backinfile.cardRouge.Res

data class EnemyActionPreview(
    val actionId: Int = 0,
    val description: String = "action$actionId",
    val tip: String = "action$actionId",
    val icon: String = Res.IMG_ICON_SLOW,
    val attack: AttackAction? = null,
)

data class AttackAction(
    val attackType: AttackType = AttackType.Pure,
    val targetSlotIndex: Int = -1,
) {

    enum class AttackType {
        Pure
    }

}
