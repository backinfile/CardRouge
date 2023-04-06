package com.backinfile.cardRouge.viewGroup

import com.almasb.fxgl.dsl.FXGL
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Log
import kotlin.reflect.KClass

object ViewGroupManager {

    private val instanceMap: HashMap<KClass<out BaseViewGroup>, MutableList<BaseViewGroup>> = hashMapOf()
    fun show(viewGroupClass: KClass<out BaseViewGroup>) {
        if (viewGroupClass in instanceMap) {
            val anyInstance = instanceMap[viewGroupClass]!![0]
            if (!anyInstance.multiInstance) {
                Log.viewGroup.error("create multiInstance of {}", viewGroupClass.simpleName)
            }
            return
        }

        val viewGroup = viewGroupClass.java.getConstructor().newInstance() as BaseViewGroup
        instanceMap.computeIfAbsent(viewGroupClass) { arrayListOf() }.add(viewGroup)
        FXGL.getGameScene().addUINode(viewGroup)
        viewGroup.onShow()
        if (Config.LOG_VIEW_GROUP_SHOW_HIDE) {
            Log.viewGroup.info("{} show", viewGroupClass.simpleName)
        }
    }

    fun destroy(viewGroupClass: KClass<out BaseViewGroup>) {
        val instance = instanceMap[viewGroupClass]?.let { if (it.isNotEmpty()) it.removeLast() else null }

        if (instance != null) {
            instance.onHide()

            if (Config.LOG_VIEW_GROUP_SHOW_HIDE) {
                Log.viewGroup.info("{} hide", viewGroupClass.simpleName)
            }
        }
    }
}