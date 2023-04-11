package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.viewGroup.Views
import com.backinfile.support.func.Action0

class BoardButtonsUtil {
}

private val buttonInfos = ArrayList<ButtonInfo>()

object BoardButtonContext {
    fun button(type: String, onClick: Action0? = null) {
        buttonInfos.add(ButtonInfo(type, onClick))
    }
}

fun Views.showButtons(block: BoardButtonContext.() -> Unit) {
    buttonInfos.clear()
    BoardButtonContext.apply(block)

    val uiGroup = this.show<BoardButtonsUIGroup>()
    for ((i, info) in buttonInfos.withIndex()) {
        uiGroup.setButton(i, info.type, info.onClick)
    }
    for (i in uiGroup.getButtonSize() downTo buttonInfos.size) {
        uiGroup.removeButton(i)
    }
}

fun Views.hideButtons() {
    this.hide<BoardButtonsUIGroup>()
}


private class ButtonInfo(val type: String, val onClick: Action0? = null)