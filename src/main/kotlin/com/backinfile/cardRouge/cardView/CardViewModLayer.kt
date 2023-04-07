package com.backinfile.cardRouge.cardView

annotation class CardViewModLayer(val layer: Layer = Layer.Default) {

    enum class Layer {
        Bottom,
        Image,
        Border,
        Default,
        Top,
    }
}

