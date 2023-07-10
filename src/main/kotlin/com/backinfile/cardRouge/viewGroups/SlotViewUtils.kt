package com.backinfile.cardRouge.viewGroups

import com.almasb.fxgl.core.math.Vec2
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.ViewOrder
import com.backinfile.cardRouge.card.CardSlot
import com.backinfile.cardRouge.card.support.CardCrystal
import com.backinfile.cardRouge.cardView.CardViewManager
import com.backinfile.support.kotlin.f

object SlotViewUtils {
    private const val cardScale = Config.SCALE_SLOT_CARD
    private const val moveHighOffset = 90.0;
    private const val cardCenterDistance = 20.0;
    private const val cardGep = 20.0;

    private val screenXCenter = Config.SCREEN_WIDTH / 2.0;
    private val screenYCenter = Config.SCREEN_HEIGHT / 2.0 - moveHighOffset;

    fun initSlotPosition(slots: Map<Int, CardSlot>, isPlayer: Boolean) {

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
                viewOrder = ViewOrder.CARD_BOARD_BACK.viewOrder()
            )
        }
    }

    fun findNearestSlot(slots: Map<Int, CardSlot>, curPos: Vec2, distanceLimit: Double): Int {
        var minIndex = -1;
        var minDistance = 0.0;
        for ((index, slot) in slots) {
            if (slot.seal) continue
            val distance = slot.position.distance(curPos)
            if (distance > distanceLimit) continue
            if (minIndex < 0 || distance > minDistance) {
                minIndex = index
                minDistance = distance
            }
        }
        return minIndex
    }
}