package com.backinfile.cardRouge.card.element

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardConfig

class CardFire : Card() {
    override val confCard: CardConfig
        get() = CardConfig(
            id = this::class.simpleName!!,
            title = "火",
            family = GameConfig.FAMILY_ELEMENT,
            cardType = GameConfig.CARD_TYPE_UNIT,
            subType = "元素",
            rare = GameConfig.RARE_INIT,
            cost = 1,
            description = "",
            image = "card/fire.png"
        )
}