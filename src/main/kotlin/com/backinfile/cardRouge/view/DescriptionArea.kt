package com.backinfile.cardRouge.view

import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.cardView.ConstCardSize
import com.backinfile.cardRouge.sticker.StickerType
import com.backinfile.support.SysException
import com.backinfile.support.fxgl.FXGLUtils
import com.backinfile.support.fxgl.opacityColor
import com.backinfile.support.fxgl.setSize
import com.backinfile.support.kotlin.d
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.scene.text.TextFlow

class DescriptionArea(val text: String) : Group() {

    init {
        initArea()
    }

    companion object {
        val group_width = ConstCardSize.inner_width * 0.9
        val group_height = ConstCardSize.maskHeight - ConstCardSize.edge_size

    }

    // [弃置] [火] [1] => [攻击] [火] [1] ;
    private fun initArea() {
        this.children.clear()

        val allParts = text.split(';').map { parseLine(it) }


        val centerX = group_width / 2
        val centerY = group_height / 2

        val font_size = 18


        val totalGroup = VBox()
        totalGroup.setSize(group_width, group_height)
        totalGroup.translateX = -group_width / 2.0
        totalGroup.translateY = -group_height / 2.0
        totalGroup.alignment = Pos.CENTER

        for (line in allParts) {
            val symbolList = if (line.condition.isNotEmpty()) line.condition + Symbol() + line.effect else line.effect
            val lineGroup = HBox()
            lineGroup.setSize(group_width, font_size * 1.5)
            lineGroup.alignment = Pos.CENTER
            lineGroup.background = Background(BackgroundFill(opacityColor(0.8), CornerRadii(2.0), null))
            totalGroup.children.add(lineGroup)
            for (symbol in symbolList) {
                if (symbol.text != null) {
                    val label = Label(symbol.text)
                    label.font = FXGLUtils.font(font_size, FontWeight.BLACK)
                    label.textFill = Color.WHITE
                    lineGroup.children.add(label)
                } else if (symbol.stickerType != null && symbol.stickerType.type == StickerType.Type.Number) {
                    val label = Label(symbol.stickerType.context)
                    label.font = FXGLUtils.font(font_size, FontWeight.BLACK)
                    label.textFill = Color.WHITE
                    label.background = Background(BackgroundFill(Color.GRAY, CornerRadii(5.0), null))
                    lineGroup.children.add(label)
                } else if (symbol.stickerType != null) {
                    val image = ImageView(Res.loadImage(symbol.stickerType.image))
                    image.fitWidth = font_size.d * 1.5
                    image.fitHeight = font_size.d * 1.5
                    lineGroup.children.add(image)
                } else {
                    val image = ImageView(Res.loadImage("sticker/rightArrow.png"))
                    image.fitWidth = font_size.d * 1.5
                    image.fitHeight = font_size.d * 1.5
                    lineGroup.children.add(image)
                }
            }
        }
        this.children.add(totalGroup)
    }

    private fun parseLine(line: String): Line {
        if ("=>" in line) {
            val (condition, effect) = line.split("=>").map { parseSymbols(it) }
            return Line(condition, effect)
        }
        return Line(listOf(), parseSymbols(line))
    }

    private fun parseSymbols(str: String): List<Symbol> {
        val result = ArrayList<Symbol>()

        for (word in str.split(' ').map { it.trim() }) {
            if (word.length > 2 && word.startsWith('[') && word.endsWith(']')) {
                val content = word.substring(1, word.lastIndex)
                val stickerType = StickerType.parse(content) ?: throw SysException("parse $word error")
                result.add(Symbol(null, stickerType))
            } else if (word.isBlank()) {
                continue
            } else {
                result.add(Symbol(word, null))
            }
        }
        return result
    }

    private data class Symbol(val text: String? = null, val stickerType: StickerType? = null) {
        override fun toString(): String {
            return text ?: stickerType.toString()
        }
    }

    private data class Line(val condition: List<Symbol>, val effect: List<Symbol>)
}

fun main() {
    val text = "[弃置] 全部 [火] [1] => [攻击] [火] [1]"
    DescriptionArea(text)
}