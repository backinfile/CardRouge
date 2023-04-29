package com.backinfile.cardRouge.sticker


/**
 * 可接可不接（抽取1/抽取1火元素），不接（修复1），施加（雷闪/打击/永燃）
 */
enum class StickerType(val type: Type, val content: String, val image: String = "") {
    ActionAttack(Type.Action, "攻击", "sticker/attack.png"),
    ActionDiscard(Type.Action, "弃置", "sticker/card.png"),

    NumberAddOne(Type.Number, "+1"),
    NumberOnce(Type.Number, "1"),

    ElementFire(Type.Element, "火", "sticker/fire.png"),

    KeywordTaunt(Type.Keyword, "嘲讽", "sticker/Taunt.png"),

    ;

    enum class Type {
        Number,
        Action,
        Element,
        Keyword,
    }

    companion object {
        fun parse(text: String): StickerType? {
            return StickerType.values().firstOrNull { text == it.content }
        }
    }

}

