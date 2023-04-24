package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.ViewOrder
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.card.CardSlot
import com.backinfile.cardRouge.card.support.CardCrystal
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.cardRouge.gen.config.ConfCard
import com.backinfile.support.kotlin.f

object SlotViewUtils {
    private const val cardScale = Config.SCALE_SLOT_CARD

    fun initSlotPosition(slots: Map<Int, CardSlot>, isPlayer: Boolean) {
        val moveHighOffset = 25.0;
        val cardCenterDistance = 20.0;
        val cardGep = 20.0;

        val screenXCenter = Config.SCREEN_WIDTH / 2.0;
        val screenYCenter = Config.SCREEN_HEIGHT / 2.0 - moveHighOffset;

        for (index in 0 until slots.size) {
            val indexOffset = index - slots.size / 2.0 + 0.5

            val targetX: Double = screenXCenter + indexOffset * (Config.CARD_WIDTH * cardScale + cardGep)
            val targetY: Double = if (isPlayer) screenYCenter + cardCenterDistance + Config.CARD_HEIGHT * cardScale / 2f
            else screenYCenter - cardCenterDistance - Config.CARD_HEIGHT * cardScale / 2f

            slots[index]!!.position.set(targetX.f, targetY.f)
        }
    }

    fun createSlotsView(slots: Map<Int, CardSlot>, isPlayer: Boolean) {
        for (slot in slots.values) {
            val card = CardCrystal()
            slot.crystal = card

            val cardView = CardViewManager.getOrCreate(card)
            cardView.modInteract.setDark(true)
            cardView.modMove.move(
                pos = slot.position.toPoint2D(),
                scale = this.cardScale,
                viewOrder = ViewOrder.CARD_BOARD_BACK.order()
            )
        }
    }

}