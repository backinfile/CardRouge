package com.backinfile.cardRouge.buff


abstract class KeywordBuff(keywordId: String) : Buff() {

    init {
    }

    override val name: String
        get() = "[Keyword]"

}