package com.backinfile.cardRouge.viewGroup

import com.almasb.fxgl.dsl.FXGL
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Log
import kotlin.reflect.KClass

object ViewGroupManager {

    private val instanceMap: HashMap<KClass<out BaseViewGroup>, MutableList<BaseViewGroup>> = hashMapOf()

    private val cacheInstanceMap: HashMap<KClass<out BaseViewGroup>, BaseViewGroup> = hashMapOf()


    fun show(viewGroupClass: KClass<out BaseViewGroup>) {
        if (viewGroupClass in instanceMap) {
            val anyInstance = instanceMap[viewGroupClass]!![0]
            if (!anyInstance.multiInstance) {
                Log.viewGroup.error("create multiInstance of {}", viewGroupClass.simpleName)
            }
            return
        }

        val instance =
            cacheInstanceMap[viewGroupClass] ?: viewGroupClass.java.getConstructor().newInstance() as BaseViewGroup
        instanceMap.computeIfAbsent(viewGroupClass) { arrayListOf() }.add(instance)
        FXGL.getGameScene().addUINode(instance)
        instance.onShow()
        if (Config.LOG_VIEW_GROUP_SHOW_HIDE) {
            Log.viewGroup.info("{} show", viewGroupClass.simpleName)
        }

        if (instance.multiInstance) {
            cacheInstanceMap[viewGroupClass] = instance
        }
    }

    fun hide(viewGroupClass: KClass<out BaseViewGroup>) {
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