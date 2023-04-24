package com.backinfile.cardRouge.dungeon

class DungeonData(val deck: MutableList<CardData>, randSeed: Int, randCnt: Int) {
    data class CardData(val id: String, val stickers: List<StickerData?>)
    data class StickerData(val id: String)
}

