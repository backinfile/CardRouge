package com.backinfile.cardRouge.card

import com.backinfile.cardRouge.action.Context

typealias StepSelectFunc = Context.(input: List<Card>) -> StepSelectResult

// 需要选择打出目标
data class CardPlayTargetInfo(
    val tip: String = "",
    val selectSlotAsMinion: Boolean = false,
    val cancelable: Boolean = true,
    val stepSelectFunc: StepSelectFunc? = null,
) {

    companion object {
        fun buildSelectCondition(cards: List<Card>, cnt: Int = 1, optional: Boolean = false): CardPlayTargetInfo {
            return CardPlayTargetInfo { selected ->
                StepSelectResult(
                    selected = selected,
                    done = selected.size == cnt,
                    nextSelectFrom = cards.filter { it !in selected },
                    cancelable = true,
                    optional = optional,
                    error = if (selected.any { it !in cards } || selected.size != selected.distinct().size) "selected other cards" else null
                )
            }
        }
    }
}


data class StepSelectResult(
    val selected: List<Card>, // 已选择
    val done: Boolean = false, // 当前选择可以点确定
    val nextSelectFrom: List<Card> = listOf(), // 当前可选择
    val cancelable: Boolean = false, // 是否可取消当前已选择的目标
    val optional: Boolean = false, // 是否可以取消打出
    val error: String? = null, // 发生未知错误取消选择
)