package com.backinfile.cardRouge.action

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.viewGroups.BoardButtonsUIGroup
import com.backinfile.cardRouge.viewGroups.ButtonInfo
import com.backinfile.cardRouge.viewGroups.ButtonsParam
import javafx.beans.property.SimpleBooleanProperty

object OperationActions {
    suspend fun Context.waitPressTurnEnd() {
        val confirmProperty = SimpleBooleanProperty(false)
        BoardButtonsUIGroup.show(ButtonsParam(ButtonInfo(Res.TEXT_TURN_END) {
            confirmProperty.set(true)
        }))

        board.waitCondition(confirmProperty) { it }

        BoardButtonsUIGroup.hide()
    }


    suspend fun Context.selectCardFrom(cards: List<Card>, cnt: Int = 1, optional: Boolean = false): List<Card> {
        val confirmProperty = SimpleBooleanProperty(false)
        val selected = ArrayList<Card>()

        BoardButtonsUIGroup.show()
        BoardButtonsUIGroup.setButton(0, Res.TEXT_CONFIRM)
        if (optional) {
            BoardButtonsUIGroup.setButton(1, Res.TEXT_CANCEL) { confirmProperty.set(true) }
        }

        for (card in cards) {
            val cardView = CardViewManager.getOrCreate(card)
            cardView.modInteract.disableAll()
            cardView.modInteract.enableClick {
                if (it.card in selected) {
                    selected.remove(it.card)
                } else {
                    selected.add(it.card)
                }

                if (selected.size == cnt) {
                    BoardButtonsUIGroup.setButton(0, Res.TEXT_CONFIRM) { confirmProperty.set(true) }
                } else {
                    BoardButtonsUIGroup.setButton(0, Res.TEXT_CONFIRM)
                }
            }
            cardView.modInteract.setDark(false)
        }


        board.waitCondition(confirmProperty) { it } // 等待满足条件

        // 清理UI
        BoardButtonsUIGroup.hide()

        for (card in cards) {
            val cardView = CardViewManager.getOrCreate(card)
            cardView.modInteract.disableAll()
        }

        return selected
    }
}