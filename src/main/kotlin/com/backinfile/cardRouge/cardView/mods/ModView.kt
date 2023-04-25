package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewBaseMod
import com.backinfile.cardRouge.cardView.CardViewModLayer
import com.backinfile.cardRouge.cardView.ConstCardSize
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

@CardViewModLayer(CardViewModLayer.Layer.Image)
class ModView(cardView: CardView) : CardViewBaseMod(cardView) {
    private val edgeView = ImageView()
    private val edgeDarkerView = Rectangle(ConstCardSize.inner_width, ConstCardSize.inner_height)

    private val imageView = ImageView()

    override fun onCreate(): Unit = with(ConstCardSize) {
        super.onCreate()

        edgeView.image = Res.loadImage(getEdgeByType())
        edgeView.fitWidth = card_width
        edgeView.fitHeight = card_height
        edgeView.x = -card_width_half
        edgeView.y = -card_height_half
        cardView.controlGroup.children.add(edgeView);

        edgeDarkerView.strokeWidth = 2.0
        edgeDarkerView.stroke = STROKE_EDGE_DARK
        edgeDarkerView.fill = Color.TRANSPARENT
        edgeDarkerView.x = -card_width_half + edge_size
        edgeDarkerView.y = -card_height_half + edge_size
        cardView.controlGroup.children.add(edgeDarkerView)


        imageView.image = Res.loadCardImage(cardView.card.confCard, true)
        imageView.fitWidth = inner_width
        imageView.fitHeight = inner_height
        imageView.x = -card_width_half + edge_size
        imageView.y = -card_height_half + edge_size
        cardView.controlGroup.children.add(imageView)
    }

    override fun onShapeChange() {
        super.onShapeChange()

        edgeView.isVisible = !cardView.shape.turnBack
        edgeDarkerView.isVisible = !cardView.shape.turnBack

        imageView.image = Res.loadCardImage(cardView.card.confCard, !cardView.shape.turnBack);

    }


    private fun getEdgeByType(): String {
        return when (cardView.card.confCard.cardType) {
            GameConfig.CARD_TYPE_UNIT -> Res.IMG_EDGE1
            GameConfig.CARD_TYPE_POWER -> Res.IMG_EDGE4
            GameConfig.CARD_TYPE_ACTION -> Res.IMG_EDGE2
            GameConfig.CARD_TYPE_SUPPORT -> Res.IMG_EDGE3
            else -> Res.IMG_EDGE1
        }
    }
}