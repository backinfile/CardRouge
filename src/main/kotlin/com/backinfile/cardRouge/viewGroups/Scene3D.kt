package com.backinfile.cardRouge.viewGroups

import javafx.scene.*


class Scene3D(root: Parent?, width: Double, height: Double, depthBuffer: Boolean, antiAliasing: SceneAntialiasing?) :
    SubScene(root, width, height, depthBuffer, antiAliasing) {

        init {
            val camera = PerspectiveCamera(true)
            camera.translateZ = -30.0
            val group = Group()
//            group.children.add(ImageView(Res.loadCardImage(ConfCard.get(GameConfig.CARD_ID_CRYSTAL))))
            val subScene = SubScene(group, 1280.0, 700.0, true, SceneAntialiasing.BALANCED)
            this.camera = camera
        }
}
