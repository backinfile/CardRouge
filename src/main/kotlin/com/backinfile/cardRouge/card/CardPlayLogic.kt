package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.action.Context


object CardPlayLogic {


    fun calcCardPlayableState(context: Context, card: Card): CardPlayTargetInfo? {
        return null
    }

    suspend fun Context.playCard(card: Card, target: List<Card>) {
        // cost mana
    }

}