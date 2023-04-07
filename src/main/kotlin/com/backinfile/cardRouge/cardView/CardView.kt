package com.backinfile.cardRouge.cardView

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.card.Card
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.image.ImageView

class CardView(val card: Card) : Group() {

    private val controlGroup = Group()
    val moveInfo = CardMoveInfo()

    init {
        this.children.add(controlGroup)
        initView()

        moveInfo.bindBy(this)
    }


    private companion object S {
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
    }


    private fun initView() {
        val imageView = ImageView()
        imageView.image = Res.loadCardTexture(card.confCard, true).image
        imageView.fitWidth = inner_width
        imageView.fitHeight = inner_height
        imageView.x = -card_width_half + edge_size
        imageView.y = -card_height_half + edge_size
        this.controlGroup.children.add(imageView)
    }

    fun update(delta: Double) {
        moveInfo.update(delta)
    }

}