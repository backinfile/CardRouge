package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.ViewOrder
import com.backinfile.cardRouge.viewGroup.Param
import com.backinfile.cardRouge.viewGroup.BaseSingleViewGroup

object BoardBackgroundGroup : BaseSingleViewGroup<Param>() {
    init {
        viewOrder = ViewOrder.BACKGROUND.viewOrder()
        addMask(0.8)
    }
}