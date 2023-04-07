package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.cardView.*
import javafx.scene.image.ImageView


@CardViewModLayer(CardViewModLayer.ModLayer.Image)
class ModMainImage(cardView: CardView) : CardViewBaseMod(cardView) {
    override fun onCreate(): Unit = with(CardSize) {

        val imageView = ImageView()
        imageView.image = Res.loadCardImage(cardView.card.confCard, true)
        imageView.fitWidth = inner_width
        imageView.fitHeight = inner_height
        imageView.x = -card_width_half + edge_size
        imageView.y = -card_height_half + edge_size
        cardView.controlGroup.children.add(imageView)
    }
}