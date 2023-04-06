package com.backinfile.cardRouge.cardView

import javafx.beans.property.SimpleObjectProperty

class MovingField<T : Number>(initValue: T) {
    var curValue = SimpleObjectProperty(initValue)
        private set
    var to = initValue
        private set
    var from = initValue
        private set
    var moving = false
        private set
}