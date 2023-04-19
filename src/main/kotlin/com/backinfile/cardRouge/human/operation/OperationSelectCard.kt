package com.backinfile.cardRouge.human.operation

import com.almasb.fxgl.core.Updatable
import com.backinfile.cardRouge.Game
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardViewManager
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class OperationSelectCard(continuation: Continuation<List<Card>>,
                          val cards: List<Card>,
                          val cnt: Int = 1,
                          val optional: Boolean = false) : PlayerOperation<List<Card>>(continuation) {
    override suspend fun startAsync() {
        val selected = ArrayList<Card>()
        for (card in cards) {
            val cardView = CardViewManager.getOrCreate(card)
            cardView.modInteract.disableAll()
            cardView.modInteract.enableClick {
                if (it.card in selected) selected.remove(it.card) else selected.add(it.card)
            }
            cardView.modInteract.setDark(false)
        }

        waitCondition { selected.size == cnt }

        continuation.resume(selected)
    }
}

