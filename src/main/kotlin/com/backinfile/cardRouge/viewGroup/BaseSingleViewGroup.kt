package com.backinfile.cardRouge.viewGroup

import com.almasb.fxgl.dsl.getGameScene
import com.backinfile.cardRouge.Config
import com.backinfile.support.kotlin.d
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.lang.reflect.Constructor
import java.lang.reflect.ParameterizedType

/**
 * 所有单例形式的UI组
 * 建议用object继承此类
 */
abstract class BaseSingleViewGroup<P : Param> : Group() {
    private var showing = false

    fun show(param: P? = null) {
        if (showing) return
        showing = true
        onShow(param ?: paramConstructor.newInstance())
        getGameScene().addUINode(this)
    }

    fun hide() {
        if (!showing) return
        showing = false
        getGameScene().removeUINode(this)
        onHide()
    }


    protected fun addMask(opacity: Double = 1.0) {
        val mask = Rectangle(Config.SCREEN_WIDTH.d, Config.SCREEN_HEIGHT.d)
        mask.fill = Color(0.0, 0.0, 0.0, opacity)
        this.children.add(mask)
    }


    protected open fun onShow(param: P) {
    }

    protected open fun onHide() {
    }


    @Suppress("UNCHECKED_CAST")
    private val paramConstructor: Constructor<P> by lazy {
        val genericSuperclass = this::class.java.genericSuperclass as ParameterizedType
        val paramClass = genericSuperclass.actualTypeArguments[0] as Class<*>
        paramClass.constructors[0] as Constructor<P>
    }
}