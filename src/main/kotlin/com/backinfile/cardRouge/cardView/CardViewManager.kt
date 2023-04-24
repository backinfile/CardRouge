package com.backinfile.cardRouge.cardView

import com.almasb.fxgl.dsl.getGameScene
import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.card.Card
import javafx.scene.Group

object CardViewManager :
    CardViewContainer("Global", { getGameScene().addUINode(it) }, { getGameScene().removeUINode(it) }) {


}


open class CardViewContainer(
    private val name: String,
    private val addUI: (CardView) -> Unit,
    private val removeUI: (CardView) -> Unit,
) {
    constructor(name: String, group: Group) : this(name, { group.children.add(it) }, { group.children.remove(it) })

    private val cardViewMap = HashMap<Card, CardView>()

    fun get(card: Card): CardView? {
        return cardViewMap[card]
    }

    fun getOrCreate(card: Card): CardView {
        if (cardViewMap.containsKey(card)) {
            return cardViewMap[card]!!
        }
        val cardView = CardView(card)
        cardViewMap[card] = cardView
        Log.game.info("create card view id:{} in:{}", card.confCard.id, name)

        return cardView.also(addUI)
    }

    fun remove(card: Card): CardView? {
        Log.game.info("remove card view id:{} in:{}", card.confCard.id, name)
        return cardViewMap.remove(card)?.also(removeUI)
    }

    fun clear() {
        Log.game.info("clear all card view in:{}", name)
        cardViewMap.clear()
    }

    fun update(delta: Double) {
        for (cardView in cardViewMap.values) {
            cardView.update(delta)
        }
    }
}