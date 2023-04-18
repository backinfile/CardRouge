package com.backinfile.support.fxgl

import javafx.scene.layout.Region
import javafx.scene.paint.Color

class FxUtils {
}

fun opacity(opacity: Double) = Color(0.0, 0.0, 0.0, opacity)

fun Region.setSize(width: Double, height: Double) {
    this.setPrefSize(width, height)
    this.setMaxSize(width, height)
    this.setMinSize(width, height)
}
