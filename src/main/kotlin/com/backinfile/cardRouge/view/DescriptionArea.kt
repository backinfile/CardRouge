package com.backinfile.cardRouge.view

import com.backinfile.cardRouge.cardView.ConstCardSize
import com.backinfile.cardRouge.sticker.StickerType
import com.backinfile.support.SysException
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Group
import java.util.regex.Pattern

class DescriptionArea(text: String) : Group() {
    var text = SimpleStringProperty(text)
        set(value) {
            field = value
            initArea()
        }

    init {
        initArea()
    }

    companion object {
        val group_width = ConstCardSize.card_width - ConstCardSize.edge_size * 2.0
        val group_height = ConstCardSize.card_width - ConstCardSize.edge_size * 2.0

    }

    // [弃置] [火] [1] => [攻击] [火] [1] ;
    private fun initArea() {
        this.children.clear()

        val allParts = text.value.split(';').map { parseLine(it) }

        println(allParts)

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

        var index = 0
        while (index < str.length) {
            val curChar = str[index]
            if (curChar in " \t\n") continue
            if (curChar == '[') {
                val nextIndex = str.indexOf(']', startIndex = index)
                if (nextIndex < 0) throw SysException("not march ]")
                val content = str.substring(index, nextIndex + 1)

            }

        }

        for (word in str.split("""( (?=\[))|((?<=\]) )""")) {
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

    private data class Symbol(val text: String?, val stickerType: StickerType?)
    private data class Line(val condition: List<Symbol>, val effect: List<Symbol>)
}

fun main() {
    val text = "[弃置] [火] [1] => [攻击] [火] [1]"
    DescriptionArea(text)
    println(text.split("""(?=\[)|(?<=\])""").joinToString { "#" })
}