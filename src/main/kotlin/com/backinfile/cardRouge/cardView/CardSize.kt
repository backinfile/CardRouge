package com.backinfile.cardRouge.cardView

import com.backinfile.cardRouge.Config
import javafx.scene.paint.Color

object CardSize {
    val card_width: Double = Config.CARD_WIDTH
    val card_height: Double = Config.CARD_HEIGHT
    val card_width_half: Double = Config.CARD_WIDTH / 2f
    val card_height_half: Double = Config.CARD_HEIGHT / 2f
    const val edge_size = 10
    const val glow_edge_size = 5
    val inner_width = card_width - edge_size * 2
    val inner_height = card_height - edge_size * 2
    val maskHeight: Double = card_width * 6 / 14
    val maskHeightOffset: Double = card_height * 3 / 14
    const val mana_offset = -edge_size / 2
    const val mana_size = 46
    const val title_font_size = 24
    const val title_height = title_font_size + 4
    const val subType_font_size = 20
    const val description_font_size = 20
    val mark_size: Double = card_height / 10f
    val selected_mark_size = mark_size * 4


    val STROKE_EDGE_DARK = Color(0.0, 0.0, 0.0, 0.3)
}