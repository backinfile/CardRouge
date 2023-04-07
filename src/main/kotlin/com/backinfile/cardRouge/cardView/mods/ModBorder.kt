package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.cardView.*
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

@CardViewModLayer(CardViewModLayer.ModLayer.Border)
class ModBorder(cardView: CardView) : CardViewBaseMod(cardView) {

    override fun onCreate(): Unit = with(CardSize) {
        val edgeView = ImageView()
        edgeView.image = Res.loadImage(Res.IMG_EDGE1)
        edgeView.fitWidth = card_width
        edgeView.fitHeight = card_height
        edgeView.x = -card_width_half
        edgeView.y = -card_height_half
        cardView.controlGroup.children.add(edgeView);

        val edgeDarkerView = Rectangle(inner_width, inner_height)
        edgeDarkerView.strokeWidth = 2.0
        edgeDarkerView.stroke = STROKE_EDGE_DARK
        edgeDarkerView.fill = Color.TRANSPARENT
        edgeDarkerView.x = -card_width_half + edge_size
        edgeDarkerView.y = -card_height_half + edge_size
        cardView.controlGroup.children.add(edgeDarkerView)
    }

}