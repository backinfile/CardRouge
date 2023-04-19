package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewBaseMod
import com.backinfile.cardRouge.cardView.CardViewModLayer
import com.backinfile.cardRouge.cardView.ConstCardSize
import com.backinfile.support.fxgl.FXGLUtils
import com.backinfile.support.fxgl.intMap
import com.backinfile.support.kotlin.d
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import java.lang.String
import kotlin.with

@CardViewModLayer(CardViewModLayer.Layer.MANA)
class ModMana(cardView: CardView) : CardViewBaseMod(cardView) {
    private val group = Group()
    var cost = SimpleIntegerProperty(cardView.card.calcCost())

    override fun onCreate() {
        with(ConstCardSize) {

            val manaView = ImageView()
            manaView.image = Res.loadImage(Res.IMG_MANA)
            manaView.fitWidth = mana_size.d
            manaView.fitHeight = mana_size.d
            manaView.opacity = 0.8
            manaView.translateX = -mana_size / 2.0
            manaView.translateY = -mana_size / 2.0

            val manaText = Label(String.valueOf(cardView.card.confCard.cost))
            manaText.font = FXGLUtils.font(26, FontWeight.BLACK)
            manaText.alignment = Pos.CENTER
            manaText.setPrefSize(mana_size.d, mana_size.d)
            manaText.translateX = -mana_size / 2.0
            manaText.translateY = -mana_size / 2.0
            manaText.textFill = Color.WHITE
            manaText.textProperty().bind(cost.intMap { if (it >= 0) it.toString() else "" })

            group.translateX = -card_width_half + mana_offset
            group.translateY = -card_height_half + mana_offset

            group.children.addAll(manaView, manaText)

            cardView.controlGroup.children.add(group)
        }
    }

    override fun onShapeChange() {
        group.isVisible = !cardView.shape.turnBack
    }
}
