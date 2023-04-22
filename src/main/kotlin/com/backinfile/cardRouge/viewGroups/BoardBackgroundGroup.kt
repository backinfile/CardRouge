package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.ViewConfig
import com.backinfile.cardRouge.viewGroup.Param
import com.backinfile.cardRouge.viewGroup.BaseSingleViewGroup

object BoardBackgroundGroup : BaseSingleViewGroup<Param>() {
    init {
        viewOrder = ViewConfig.Z_BACKGROUND
        addMask(0.8)
    }
}