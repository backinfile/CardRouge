package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.cardView.CardView

interface CardDragCallback {
    val triggerCancel get() = true

    fun start(cardView: CardView) {

    }

    fun update(cardView: CardView) {

    }

    fun over(cardView: CardView) {

    }

    fun cancel(cardView: CardView) {

    }
}