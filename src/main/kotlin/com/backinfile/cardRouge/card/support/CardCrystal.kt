package com.backinfile.cardRouge.card.support

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardConfig

class CardCrystal : Card() {
    override val confCard: CardConfig
        get() = CardConfig(
            id = this::class.simpleName!!,
            cardType = GameConfig.CARD_TYPE_SUPPORT,
            image = "crystal.png",
            backImage = "crystal_broken.png"
        )
}