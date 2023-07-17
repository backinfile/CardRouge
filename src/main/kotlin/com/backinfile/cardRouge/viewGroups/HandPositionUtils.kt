package com.backinfile.cardRouge.viewGroups

import com.almasb.fxgl.core.math.FXGLMath
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.ViewConfig
import com.backinfile.cardRouge.ViewOrder
import com.backinfile.cardRouge.cardView.CardView
import javafx.geometry.Point2D
import javafx.util.Duration
import java.util.HashMap

object HandPositionUtils {

    // 卡牌在手牌中的状态信息
    data class CardInfo(val state: CardState, val index: Int, val total: Int, var toIndex: Int = -1)

    enum class CardState {
        HandNormal, HandFlow, HandHover, Drag, HandSelect
    }


    private val circleRadius: Double = ViewConfig.CARD_HEIGHT * 6 // 手牌圆的半径

    private val xCenter: Double = Config.SCREEN_WIDTH / 2.0
    private val handY: Double = Config.SCREEN_HEIGHT + ViewConfig.CARD_HEIGHT * ViewConfig.SCALE_HAND_CARD / 4.0
    private val hoverY: Double = Config.SCREEN_HEIGHT - ViewConfig.CARD_HEIGHT * ViewConfig.SCALE_HOVER_CARD / 2.0


    fun setCardState(cardView: CardView, cardInfo: CardInfo): Duration {
        val handSize = cardInfo.total
        val index = cardInfo.index
        val state = cardInfo.state

        val targetX: Double
        var targetY: Double
        val degree: Double
        if (ViewConfig.ROTATE_CARD_IN_HAND) {
            val offsetDegree = 2 + (11 - handSize) * .25f
            degree = -((handSize / 2f - index - 0.5f) * offsetDegree).toDouble()
            targetX = getPositionX(degree)
            targetY = getPositionY(degree)
        } else {
            degree = 0.0
            val indexFromCenter = index - handSize / 2f + 0.5f
            val gep: Double = ViewConfig.CARD_WIDTH * ViewConfig.SCALE_HAND_CARD * ((11 - handSize) / 11f * 0.5f + 0.5f)
            targetX = xCenter + indexFromCenter * gep
            targetY = handY // + Math.abs(indexFromCenter) * 1f;
        }
        targetY -= ViewConfig.CARD_HEIGHT * ViewConfig.SCALE_HAND_CARD / 4f
        val zIndex = ViewOrder.CARD_HAND.viewOrder() - index
        return when (state) {
            CardState.HandNormal -> cardView.modMove.move(
                pos = Point2D(targetX, targetY),
                scale = ViewConfig.SCALE_HAND_CARD,
                viewOrder = zIndex,
                rotate = degree,
                duration = ViewConfig.ANI_CARD_MOVE_TIME
            )

            CardState.HandFlow -> cardView.modMove.move(
                pos = Point2D(targetX, targetY - ViewConfig.CARD_HEIGHT * ViewConfig.SCALE_HAND_CARD / 6.0),
                scale = ViewConfig.SCALE_HAND_CARD,
                viewOrder = zIndex,
                rotate = degree,
                duration = ViewConfig.ANI_CARD_MOVE_TIME
            )

            CardState.HandSelect -> cardView.modMove.move(
                pos = Point2D(targetX, targetY - ViewConfig.CARD_HEIGHT * ViewConfig.SCALE_HAND_CARD / 4f),
                scale = ViewConfig.SCALE_HAND_CARD,
                viewOrder = zIndex,
                rotate = degree,
                duration = ViewConfig.ANI_CARD_MOVE_TIME
            )

            CardState.HandHover -> cardView.modMove.move(
                pos = Point2D(targetX, hoverY),
                scale = ViewConfig.SCALE_HOVER_CARD,
                viewOrder = ViewOrder.CARD_HOVER.viewOrder(),
                rotate = 0.0,
                duration = Duration.seconds(0.05)
            )

            CardState.Drag -> cardView.modMove.move(
                scale = ViewConfig.SCALE_DRAG_CARD,
                viewOrder = ViewOrder.CARD_DRAG.viewOrder(),
                rotate = 0.0,
                duration = Duration.seconds(0.05)
            )
        }
    }


    private val positionCacheMap: MutableMap<Double, Point2D> = HashMap()

    private fun getPositionX(degree: Double): Double {
        return getPosition(degree).x
    }

    private fun getPositionY(degree: Double): Double {
        return getPosition(degree).y
    }

    private fun getPosition(degree: Double): Point2D {
        val point2D = positionCacheMap[degree]
        if (point2D != null) return point2D

        val radius = circleRadius
        val targetX = xCenter + radius * FXGLMath.sin(FXGLMath.toRadians(degree))
        val targetY = handY + radius - radius * FXGLMath.cos(FXGLMath.toRadians(degree))
        return Point2D(targetX, targetY).also { positionCacheMap[degree] = it }
    }

}