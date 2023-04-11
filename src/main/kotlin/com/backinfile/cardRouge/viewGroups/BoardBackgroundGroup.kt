package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.viewGroup.Param
import com.backinfile.cardRouge.viewGroup.BaseViewGroup

class BoardBackgroundGroup : BaseViewGroup<Param>() {
    init {
        addMask(0.8)
    }
}