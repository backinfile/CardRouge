package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.viewGroup.Views
import com.backinfile.support.func.Action0

class Buttons {
}


internal class ButtonInfo(val type: String, val onClick: Action0? = null)

class BoardButtonContext internal constructor() {
    internal val buttonInfos = ArrayList<ButtonInfo>()

    fun button(type: String, onClick: Action0? = null) {
        buttonInfos.add(ButtonInfo(type, onClick))
    }
}

fun Views.showButtons(block: BoardButtonContext.() -> Unit) {
    val context = BoardButtonContext()
    context.apply(block)
    val uiGroup = this.show<BoardButtonsUIGroup>()

    val buttonInfos = context.buttonInfos

    for ((i, info) in buttonInfos.withIndex()) {
        uiGroup.setButton(i, info.type, info.onClick)
    }

    for (i in buttonInfos.size..uiGroup.getButtonSize()) {
        uiGroup.removeButton(i)
    }
}

fun Views.hideButtons() {
    this.hide<BoardButtonsUIGroup>()
}