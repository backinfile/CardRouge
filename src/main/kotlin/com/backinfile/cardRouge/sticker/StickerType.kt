package com.backinfile.cardRouge.sticker


enum class StickerType(
    val type: Type,
    val content: String,
    val image: String = "",
    val actionType: ActionType = ActionType.WithIt,
    val effect: Effect = Effect.None,
) {
    ActionAttack(Type.Action, "攻击", ""),
    ActionDiscard(Type.Action, "弃置", ""),

    NumberAddOne(Type.Number, "+1"),
    NumberOnce(Type.Number, "1"),

    ElementFire(Type.Element, "火", "sticker/fire.png"),
    KeywordTaunt(Type.Keyword, "嘲讽", "sticker/Taunt.png"),
    KeywordThunder(Type.Keyword, "引雷", "", effect = Effect.Negative),

    ;

    enum class Type {
        Number,
        Action,
        Element,
        Keyword,
    }

    /**
     * 可接可不接（抽取1/抽取1火元素），不接（修复1），施加（雷闪/打击/永燃）
     */
    enum class ActionType {
        WithIt,
        NoIt,
        Apply
    }

    enum class Effect {
        None,
        Positive,
        Negative
    }

    companion object {
        fun parse(text: String): StickerType? {
            return StickerType.values().firstOrNull { text == it.content }
        }
    }

}

