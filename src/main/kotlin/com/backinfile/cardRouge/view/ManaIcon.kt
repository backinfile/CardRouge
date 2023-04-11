package com.backinfile.cardRouge.view

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.support.fxgl.FXGLUtils
import com.backinfile.support.kotlin.d
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

class ManaIcon : Group() {
    private val manaText = Label()

    companion object {
        const val font_size = 20
    }

    init {
        val manaView = ImageView(Res.loadImage(Res.IMG_MANA))
        with(manaView) {
            fitWidth = font_size * 1.0
            fitHeight = font_size * 1.0
            translateX = 0.0
            translateY = -font_size * 0.5
        }
        this.children.add(manaView)


        with(manaText) {
            text = "-/-"
//            prefWidth = font_size * 2.0
            prefHeight = font_size * 2.0
            alignment = Pos.CENTER_LEFT
            font = FXGLUtils.font(font_size, FontWeight.BOLD)
            textFill = Color.WHITE
            translateX = font_size * 1.2
            translateY = -font_size * 1.0
        }
        this.children.add(manaText)
    }

    fun setMana(cur: Int?, max: Int?) {
        manaText.text = (cur?.toString() ?: "-") + "/" + (max?.toString() ?: "-")
    }
}
