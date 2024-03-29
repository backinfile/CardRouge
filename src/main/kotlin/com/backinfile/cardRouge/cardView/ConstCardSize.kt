package com.backinfile.cardRouge.cardView

import com.backinfile.cardRouge.ViewConfig
import com.backinfile.support.fxgl.opacityColor
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop

object ConstCardSize {
    val card_width: Double = ViewConfig.CARD_WIDTH
    val card_height: Double = ViewConfig.CARD_HEIGHT
    val card_width_half: Double = card_width / 2f
    val card_height_half: Double = card_height / 2f
    const val edge_size = 10
    const val glow_edge_size = 5
    val inner_width = card_width - edge_size * 2
    val inner_height = card_height - edge_size * 2
    val maskBottomOffset: Double = card_width * 0.5 / 14
    val maskHeight: Double = card_width * 7.0 / 14
    val maskHeightOffset: Double = card_height * 3.0 / 14
    const val mana_offset = edge_size * 2
    const val mana_size = 46
    const val title_font_size = 24
    const val title_height = title_font_size + 4
    const val subType_font_size = 20
    const val description_font_size = 20
    val mark_size: Double = card_height / 10f
    val selected_mark_size = mark_size * 4

    val FILL_DARK_MASK = Color(0.0, 0.0, 0.0, 0.2)
    val FILL_TEXT_BACKGROUND = Color(0.0, 0.0, 0.0, 0.5)
    val FILL_TITLE_BACKGROUND = Color(0.0, 0.0, 0.0, 0.4)
    val STROKE_EDGE_DARK = Color(0.0, 0.0, 0.0, 0.3)

    val BACKGROUND_GROUP_NUMBER = Background(BackgroundFill(Color.DARKGREEN, null, null))
    val BACKGROUND_TITLE = Background(BackgroundFill(FILL_TEXT_BACKGROUND, null, null))
    val GRADIENT_MASK = LinearGradient(
        0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
        listOf(
            Stop(0.0, opacityColor(0.0)),
            Stop(1.0, opacityColor(0.8))
        )
    )
}