package com.backinfile.cardRouge.cardView

abstract class CardViewBaseMod(val cardView: CardView) {

    open fun onCreate() {

    }

    open fun update(delta: Double) {

    }

    open fun onShapeChange(shape: CardView.Shape) {

    }
}