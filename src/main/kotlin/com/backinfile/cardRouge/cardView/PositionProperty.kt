package com.backinfile.cardRouge.cardView

import javafx.beans.property.ObjectProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Point2D

class PositionProperty : SimpleObjectProperty<Point2D>() {

    override fun set(newValue: Point2D?) {
        super.set(newValue)
    }

    override fun fireValueChangedEvent() {
        super.fireValueChangedEvent()
    }
}