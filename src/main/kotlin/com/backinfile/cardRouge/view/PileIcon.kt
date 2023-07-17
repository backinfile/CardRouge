package com.backinfile.cardRouge.view

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.ViewConfig
import com.backinfile.support.fxgl.FXGLUtils
import com.backinfile.support.kotlin.d
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class PileIcon(img: String, showBorder: Boolean = true, x: Double = 0.0, y: Double = 0.0) : Group() {
    private val pileNumber: Label = Label()

    companion object {
        val pileSize: Double = ViewConfig.PILE_SIZE.d
        const val numberFontSize = 14
    }

    init {
        val pileIcon = ImageView(Res.loadImage(img))
        with(pileIcon) {
            fitWidth = pileSize
            fitHeight = pileSize
            translateX = -pileSize / 2
            translateY = -pileSize / 2
        }

        with(pileNumber) {
            text = "-"
            prefWidth = pileIcon.fitWidth
            prefHeight = numberFontSize.d
            font = FXGLUtils.font(numberFontSize)
            translateX = -pileSize / 2
            translateY = pileIcon.fitHeight * 0.9f - pileSize / 2
            alignment = Pos.CENTER
            textFill = Color.WHITE
        }

        this.children.addAll(pileIcon, pileNumber)

        if (showBorder) {
            val border = Rectangle(pileIcon.fitWidth, pileIcon.fitHeight + numberFontSize)
            border.fill = Color.TRANSPARENT
            border.stroke = Color.WHITE
            border.isVisible = false
            border.strokeWidth = 2.0;
            this.children.add(border)
            this.setOnMouseEntered { border.isVisible = true }
            this.setOnMouseExited { border.isVisible = false }
        }

        this.translateX = x
        this.translateY = y
    }

    fun setPileSize(size: Int?) {
        pileNumber.text = size?.toString() ?: "-"
    }
}
