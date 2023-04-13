package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.gen.config.ConfCard
import javafx.fxml.FXMLLoader
import javafx.scene.Group
import javafx.scene.Parent
import javafx.scene.PerspectiveCamera
import javafx.scene.SceneAntialiasing
import javafx.scene.SubScene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane


class Scene3D(root: Parent?, width: Double, height: Double, depthBuffer: Boolean, antiAliasing: SceneAntialiasing?) :
    SubScene(root, width, height, depthBuffer, antiAliasing) {

        init {
            val camera = PerspectiveCamera(true)
            camera.translateZ = -30.0
            val group = Group()
            group.children.add(ImageView(Res.loadCardImage(ConfCard.get(GameConfig.CARD_ID_CRYSTAL))))
            val subScene = SubScene(group, 1280.0, 700.0, true, SceneAntialiasing.BALANCED)
            this.camera = camera
        }
}
