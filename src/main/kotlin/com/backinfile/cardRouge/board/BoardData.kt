package com.backinfile.cardRouge.board

import com.backinfile.cardRouge.human.HumanBase
import com.backinfile.cardRouge.human.Robot

class BoardData {
    fun createRobot(): HumanBase {
        return Robot()
    }
}