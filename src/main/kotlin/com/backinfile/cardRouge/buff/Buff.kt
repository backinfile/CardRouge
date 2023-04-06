package com.backinfile.cardRouge.buff

import com.backinfile.cardRouge.card.Card

// 所有卡牌效果
abstract class Buff() {
    lateinit var container: BuffContainer

    var count = -1
    var value = -1
    private var sourceCard: Card? = null
    var isAura = false

    open val name: String
        get() = ""

    open val description: String
        get() = ""

    val source: String
        get() = sourceCard?.getConf()?.title ?: ""


    fun source(source: Card) = this.also { sourceCard = source }


    constructor(value: Int) : this() {
        this.value = value
    }
}
