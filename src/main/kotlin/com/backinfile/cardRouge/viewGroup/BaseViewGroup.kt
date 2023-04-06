package com.backinfile.cardRouge.viewGroup

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Log
import com.backinfile.support.kotlin.d
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

/**
 * 需要有默认构造函数
 */
abstract class BaseViewGroup : Group() {
    internal var multiInstance = false

    fun addMask(opacity: Double) {
        val mask = Rectangle(Config.SCREEN_WIDTH.d, Config.SCREEN_HEIGHT.d)
        mask.fill = Color(0.0, 0.0, 0.0, opacity)
        this.children.add(mask)
    }

    open fun onShow() {
    }

    open fun onHide() {
    }
}