package com.backinfile.cardRouge.cardView

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.card.Card
import com.backinfile.cardRouge.gen.module.CardViewModules
import com.backinfile.cardRouge.gen.module.CardViewModulesInterface
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import javafx.util.Duration

class CardView(val card: Card) : Group(), CardViewModulesInterface {

    val controlGroup = Group()
    private val modules = CardViewModules(this)

    val moveInfo: CardMoveInfo get() = modMove.moveInfo

    init {
        modules.init()
        modules.onCreate()
        children.add(controlGroup)
    }


    override fun getModules() = modules

    fun update(delta: Double) {
        modules.update(delta)
    }

}