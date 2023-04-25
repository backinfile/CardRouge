package com.backinfile.cardRouge.sticker


enum class StickerType(val type: Type, val context: String, val image: String = "") {
    ActionAttack(Type.Action, "攻击"),

    NumberAddOne(Type.Number, "+1"),
    NumberOnce(Type.Number, "1"),

    ElementFire(Type.Element, "火"),

    KeywordTaunt(Type.Keyword, "嘲讽"),

    ;

    enum class Type {
        Number,
        Action,
        Element,
        Keyword,
    }

    companion object {
        fun parse(text: String): StickerType? {
            return StickerType.values().firstOrNull { text == it.context }
        }
    }

}

