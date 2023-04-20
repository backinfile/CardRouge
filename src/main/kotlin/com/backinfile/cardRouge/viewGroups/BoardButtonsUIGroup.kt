package com.backinfile.cardRouge.viewGroups

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.ViewConfig
import com.backinfile.cardRouge.viewGroup.Param
import com.backinfile.cardRouge.viewGroup.BaseSingleViewGroup
import com.backinfile.support.func.Action0
import com.backinfile.support.fxgl.FXGLUtils
import com.backinfile.support.kotlin.d
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Button
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font

class ButtonInfo(val type: String, val onClick: Action0? = null)
class ButtonsParam() : Param() {
    val buttons: ArrayList<ButtonInfo> = ArrayList()

    constructor(vararg button: ButtonInfo) : this() {
        buttons.addAll(listOf(*button))
    }
}

object BoardButtonsUIGroup : BaseSingleViewGroup<ButtonsParam>() {

    private val buttonsContainer: VBox
    private val buttons = ArrayList<Button>()
    override val reShow: Boolean get() = true


    private const val button_font_size = 20
    private val button_width: Int = Config.SCREEN_WIDTH / 10
    private const val button_height = button_font_size + 4


    init {
//        viewOrder = ViewConfig.Z_UI_OPERATOR
//        addMask(0.5)
        buttonsContainer = initContainer();
    }


    override fun onShow(param: ButtonsParam) {
        super.onShow(param)

        val buttonInfoList = param.buttons

        for ((i, info) in buttonInfoList.withIndex()) {
            setButton(i, info.type, info.onClick)
        }
        for (i in getButtonSize() downTo buttonInfoList.size) {
            removeButton(i)
        }
    }

    fun addButton(text: String, onClick: Action0? = null): Int {
        val button: Button = createButton()
        button.text = text
        button.isVisible = true
        button.isDisable = false
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
        button.isDisable = false
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