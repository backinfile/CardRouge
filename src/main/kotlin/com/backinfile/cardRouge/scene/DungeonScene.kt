package com.backinfile.cardRouge.scene

import com.almasb.fxgl.scene.SubScene
import com.backinfile.cardRouge.viewGroup.ViewGroupManager
import com.backinfile.cardRouge.viewGroups.BoardBackgroundGroup

class DungeonScene : SubScene() {

    override fun onCreate() {
        super.onCreate()

        ViewGroupManager.show(BoardBackgroundGroup::class)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onUpdate(tpf: Double) {
        super.onUpdate(tpf)
    }
}