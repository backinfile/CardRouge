package com.backinfile.cardRouge.cardView

abstract class CardViewBaseMod(val cardView: CardView) {

    open fun onCreate() {

    }

    open fun onShape(minion: Boolean, turnBack: Boolean) {
    }

    open fun update(delta: Double) {

    }
}