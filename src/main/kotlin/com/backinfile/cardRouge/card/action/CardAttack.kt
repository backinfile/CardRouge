package com.backinfile.cardRouge.card.action

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardConfig

class CardAttack : Card() {
    override val confCard: CardConfig
        get() = CardConfig(
            id = this::class.simpleName!!,
            title = "攻击",
            family = GameConfig.FAMILY_ELEMENT,
            cardType = GameConfig.CARD_TYPE_ACTION,
            subType = "",
            rare = GameConfig.RARE_INIT,
            cost = 1,
            description = "每当有[火][攻击], <br>对随机敌人施加[引雷]",
            image = "card/lv1.png"
        )
}