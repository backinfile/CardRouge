package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewBaseMod
import com.backinfile.cardRouge.cardView.CardViewModLayer
import com.backinfile.cardRouge.cardView.ConstCardSize
import com.backinfile.support.kotlin.d
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

@CardViewModLayer(CardViewModLayer.Layer.ICON)
class ModIcon(cardView: CardView) : CardViewBaseMod(cardView) {
    private val manaView = ImageView()
    private val manaBG = Circle()

    override fun onCreate() {
        with(ConstCardSize) {

            manaView.image = Res.loadImage(Res.IMG_ICON_FAST)
            manaView.fitWidth = mana_size.d
            manaView.fitHeight = mana_size.d
            manaView.opacity = 0.6
            manaView.x = -card_width_half + edge_size - mana_offset
            manaView.y = -card_height_half + edge_size - mana_offset

            manaBG.radius = mana_size / 2.0
            manaBG.centerX = manaView.x + manaBG.radius
            manaBG.centerY = manaView.y + manaBG.radius
            manaBG.fill = Color(0.0, 0.3, 0.2, 1.0)

            cardView.controlGroup.children.addAll(manaBG, manaView)
        }
    }

    override fun onShapeChange(shape: CardView.Shape) {
        manaView.isVisible = !shape.turnBack
        manaBG.isVisible = !shape.turnBack
    }
}
