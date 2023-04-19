package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.cardView.*
import javafx.scene.image.ImageView


@CardViewModLayer(CardViewModLayer.Layer.Image)
class ModMainImage(cardView: CardView) : CardViewBaseMod(cardView) {
    private val imageView = ImageView()

    override fun onCreate(): Unit = with(ConstCardSize) {
        imageView.image = Res.loadCardImage(cardView.card.confCard, true)
        imageView.fitWidth = inner_width
        imageView.fitHeight = inner_height
        imageView.x = -card_width_half + edge_size
        imageView.y = -card_height_half + edge_size
        cardView.controlGroup.children.add(imageView)
    }
    override fun onShapeChange() {
        imageView.image = Res.loadCardImage(cardView.card.confCard, !cardView.shape.turnBack);
    }
}