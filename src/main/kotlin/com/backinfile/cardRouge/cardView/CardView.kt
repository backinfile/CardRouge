package com.backinfile.cardRouge.cardView

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.gen.module.CardViewModules
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle

class CardView(val card: Card) : Group() {

    val controlGroup = Group()
    val moveInfo = CardMoveInfo()
    val modules = CardViewModules(this)

    init {
        modules.init()
        modules.onCreate()

        children.add(controlGroup)
        moveInfo.bindBy(this)
    }


    fun update(delta: Double) {
        moveInfo.update(delta)
        modules.update(delta)
    }

}