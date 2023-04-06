package com.backinfile.cardRouge.buff

import com.backinfile.cardRouge.gen.config.ConfKeyword


abstract class KeywordBuff(keywordId: String) : Buff() {
    val confKeyword: ConfKeyword;

    init {
        confKeyword = ConfKeyword.get(keywordId) ?: ConfKeyword.get("default")
    }

    override val name: String
        get() = confKeyword.name

}