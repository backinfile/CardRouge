package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.action.Context

typealias SingleSelectFunc = Context.() -> StepSelectResult
typealias StepSelectFunc = Context.(input: List<Card>) -> StepSelectResult

// 需要选择打出目标
data class CardPlayTargetInfo(
    val tip: String = "",
    val playMinion: Boolean = false,

    val stepSelectFunc: StepSelectFunc? = null,
) {
    companion object {
        // select @cnt cards from @cards
        fun selectCnt(cards: List<Card>, cnt: Int = 1, optional: Boolean = false): CardPlayTargetInfo {
            val curSelectFrom = ArrayList<Card>(cards)
            return CardPlayTargetInfo(tip = "select $cnt") { selected ->
                StepSelectResult(
                    done = selected.size == cnt,
                    optional = optional,
                    nextSelectFrom = curSelectFrom.filter { it !in selected },
                    cancelable = true,
                    error = if (selected.any { it !in cards } || selected.size != selected.distinct().size) "selected other cards" else null,
                    tip = "select $cnt",
                )
            }
        }
    }
}

data class StepSelectResult(
    val done: Boolean = false, // 当前选择可以点确定
    val optional: Boolean = false, // 是否可以取消打出

    val nextSelectFrom: List<Card> = listOf(), // 当前可选择
    val cancelable: Boolean = false, // 是否可取消当前已选择的目标

    val error: String? = null, // 发生未知错误取消选择
    val tip: String = "",
)