package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.viewGroup.BaseViewGroup
import com.backinfile.support.func.Action0
import com.backinfile.support.kotlin.d
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.scene.text.Font


class BoardButtonsUIGroup : BaseViewGroup() {

    private val buttonsContainer = initContainer()
    private val buttons = ArrayList<Button>()

    fun addButton(text: String, onClick: Action0? = null): Int {
        val button: Button = createButton()
        button.text = text
        button.isVisible = true
        button.isDisable = onClick == null
        button.onMouseClicked = if (onClick == null) null else EventHandler { onClick.invoke() }
        buttons.add(button)
        buttonsContainer.children.add(button)
        return buttons.size - 1
    }


    fun setButton(index: Int, text: String, onClick: Action0? = null) {
        if (index == buttons.size) { // 正好缺少一个button
            addButton(text, onClick)
            return
        }
        val button = buttons[index]
        button.text = text
        button.isDisable = onClick == null
        button.onMouseClicked = if (onClick == null) null else EventHandler { onClick.invoke() }
    }


    fun removeButton(index: Int) {
        if (index >= buttons.size) {
            return
        }
        val button = buttons.removeAt(index)
        buttonsContainer.children.remove(button)
    }

    fun clearAllButton() {
        for (button in buttons) buttonsContainer.children.remove(button)
        buttons.clear()
    }

    fun getButtonSize() = buttons.size


    companion object {
        private const val button_font_size = 20
        private val button_width: Int = Config.SCREEN_WIDTH / 10
        private const val button_height = button_font_size + 4
    }

    private fun createButton(): Button {
        val button = Button()
        button.setPrefSize(button_width.d, button_height.d)
        button.font = Font.font(button_font_size.toDouble())
        return button
    }

    private fun initContainer(): VBox {
        val numberOfButton = 4
        val space = 10
        val width = button_width
        val height = (button_height + space) * numberOfButton
        val offsetX = width / 4.0
        val offsetY = Config.CARD_HEIGHT * Config.SCALE_HAND_CARD
        val x = Config.SCREEN_WIDTH - width - offsetX
        val y = Config.SCREEN_HEIGHT - offsetY
        val buttonsContainer = VBox()
        buttonsContainer.alignment = Pos.TOP_CENTER
        buttonsContainer.translateX = x
        buttonsContainer.translateY = y
        buttonsContainer.spacing = 10.0
        this.children.add(buttonsContainer)
        return buttonsContainer
    }
}