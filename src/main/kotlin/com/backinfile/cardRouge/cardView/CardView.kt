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
    var shape = Shape()

    val curCard: Card get() = shape.changeCard ?: card

    data class Shape(
        val turnBack: Boolean = false,
        val minion: Boolean = false,
        val changeableCardList: List<Card> = listOf(),
        val changeCard: Card? = null,
    )

    init {
        modules.init()
        modules.onCreate()
        children.add(controlGroup)
    }


    override fun getModules() = modules

    fun update(delta: Double) {
        modules.update(delta)
    }

    fun shapeTo(shape: Shape) {
        this.shape = shape
        modules.onShapeChange()
    }

}