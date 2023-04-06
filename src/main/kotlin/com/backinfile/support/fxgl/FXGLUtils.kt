package com.backinfile.support.fxgl

import com.almasb.fxgl.dsl.FXGL
import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.support.func.Action0
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight


fun font(
    size: Int = Res.FONT_SIZE_DEFAULT,
    family: String = Res.FONT_FAMILY_DEFAULT,
    weight: FontWeight = FontWeight.NORMAL,
) = Font.font(family, weight, size.toDouble())


object FXGLUtils {


    fun btn(text: String = "", onClick: Action0? = null): Button {
        val btn = FXGL.getUIFactoryService().newButton(text)
        btn.onMouseClicked = EventHandler { onClick?.invoke() }
        return btn
    }

    fun btnNormal(text: String = "", onClick: Action0? = null): Button {
        val btn = Button(text)
        btn.styleClass.setAll("bc_button")
        btn.alignment = Pos.CENTER
        btn.minWidth = 200.0
        btn.maxWidth = 200.0
        btn.font = Font.font(Res.FONT_FAMILY_DEFAULT, FontWeight.NORMAL, 20.0)
        btn.onMouseClicked = EventHandler { onClick?.invoke() }
        return btn
    }

    fun btnBottomCenter(text: String = "", onClick: Action0? = null): Button {
        val btn = btnNormal(text, onClick)
        btn.translateXProperty().bind(btn.widthProperty().multiply(-0.5f).add(Config.SCREEN_WIDTH / 2))
        btn.translateY = Config.SCREEN_HEIGHT * 0.9
        return btn
    }

    fun labelTopCenter(text: String = ""): Label {
        val label = Label(text)
        label.font = Font.font(Res.FONT_FAMILY_DEFAULT, FontWeight.NORMAL, 24.0)
        label.textFill = Color.WHITE
        label.background = Background(
            BackgroundFill(
                Color(0.3, 0.3, 0.3, 0.99),
                null,
                null
            )
        )
        label.translateXProperty().bind(label.widthProperty().multiply(-0.5f).add(Config.SCREEN_WIDTH / 2f))
        label.translateY = (Config.SCREEN_HEIGHT * 0.07f).toDouble()
        label.alignment = Pos.CENTER
        label.minWidth = (Config.SCREEN_WIDTH / 2f).toDouble()
        label.minHeight = 36.0
        return label
    }

    fun vBox(width: Float, height: Float, vararg nodes: Node?): VBox {
        val vBox = VBox(*nodes)
        vBox.setPrefSize(width.toDouble(), height.toDouble())
        vBox.alignment = Pos.CENTER
        return vBox
    }
}
