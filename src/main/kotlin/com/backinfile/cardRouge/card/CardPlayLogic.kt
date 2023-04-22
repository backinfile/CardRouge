package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.action.Context

object CardPlayLogic {

    data class CardPlayInfo(val cards: List<Card>)

    fun calcCardPlayableState(context: Context, card: Card): Boolean {
        return false
    }

    suspend fun Context.playCard(card: Card) {
        // cost mana
    }

}