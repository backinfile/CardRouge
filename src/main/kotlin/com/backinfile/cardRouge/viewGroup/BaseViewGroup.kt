package com.backinfile.cardRouge.viewGroup

import com.backinfile.cardRouge.Config
import com.backinfile.support.kotlin.d
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

/**
 * 需要有默认构造函数
 */
abstract class BaseViewGroup<T : Param> : Group() {
    var multiInstance = false
        protected set

    fun addMask(opacity: Double) {
        val mask = Rectangle(Config.SCREEN_WIDTH.d, Config.SCREEN_HEIGHT.d)
        mask.fill = Color(0.0, 0.0, 0.0, opacity)
        this.children.add(mask)
    }

    open fun onShow(param: T) {
    }

    open fun onHide() {
    }
}