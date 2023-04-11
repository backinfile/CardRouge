package com.backinfile.cardRouge.viewGroup

import com.almasb.fxgl.dsl.FXGL
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Log
import kotlin.reflect.KClass

object Views {

    private val instanceMap: HashMap<KClass<out BaseViewGroup<*>>, MutableList<BaseViewGroup<*>>> = hashMapOf()

    private val cacheInstanceMap: HashMap<KClass<out BaseViewGroup<*>>, BaseViewGroup<*>> = hashMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <T : BaseViewGroup<Param>, Param : com.backinfile.cardRouge.viewGroup.Param> show(viewGroupClass: KClass<T>, param: Param? = null): T {
        if (viewGroupClass in instanceMap) {
            val anyInstance = instanceMap[viewGroupClass]!![0]
            if (!anyInstance.multiInstance) {
                return anyInstance as T
            }
        }

        val instance: T = (cacheInstanceMap[viewGroupClass] ?: viewGroupClass.java.getConstructor().newInstance()) as T
        instanceMap.computeIfAbsent(viewGroupClass) { arrayListOf() }.add(instance)
        FXGL.getGameScene().addUINode(instance)
        instance.onShow(param ?: Param() as Param)
        if (Config.LOG_VIEW_GROUP_SHOW_HIDE) {
            Log.viewGroup.info("{} show", viewGroupClass.simpleName)
        }

        if (!instance.multiInstance) {
            cacheInstanceMap[viewGroupClass] = instance
        }
        return instance
    }

    fun <T : BaseViewGroup<Param>, Param : com.backinfile.cardRouge.viewGroup.Param> hide(viewGroupClass: KClass<T>) {
        val instance = instanceMap[viewGroupClass]?.let { if (it.isNotEmpty()) it.removeLast() else null }
        if (instance == null) {
            Log.viewGroup.warn("hide a not existed viewGroup {}", viewGroupClass.simpleName)
            return
        }
        instance.onHide()
        if (Config.LOG_VIEW_GROUP_SHOW_HIDE) {
            Log.viewGroup.info("{} hide", viewGroupClass.simpleName)
        }
    }
}