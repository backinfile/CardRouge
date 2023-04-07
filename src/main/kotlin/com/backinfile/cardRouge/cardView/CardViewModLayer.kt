package com.backinfile.cardRouge.cardView

annotation class CardViewModLayer(val layer: ModLayer = ModLayer.Default) {

    enum class ModLayer {
        Bottom,
        Image,
        Border,
        Default,
        Top,
    }
}

