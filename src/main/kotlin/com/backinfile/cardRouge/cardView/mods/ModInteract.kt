package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Game
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.ViewConfig
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewBaseMod
import com.backinfile.cardRouge.cardView.CardViewModLayer
import com.backinfile.cardRouge.cardView.ConstCardSize
import com.backinfile.support.fxgl.clamp
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.util.Duration

private typealias CardInteractCallback = (CardView) -> Unit

@CardViewModLayer(CardViewModLayer.Layer.Control)
class ModInteract(cardView: CardView) : CardViewBaseMod(cardView) {
    private val darkMaskView =
        Rectangle(ConstCardSize.card_width, ConstCardSize.card_height, ConstCardSize.FILL_DARK_MASK)
    private val selected: ImageView = ImageView(Res.loadImage(Res.IMG_SELECTED_MARK))
    private val noSelect: ImageView = ImageView(Res.loadImage(Res.IMG_NO_SELECT_MARK))
    private val controlMask = Rectangle(ConstCardSize.card_width, ConstCardSize.card_height, Color.BLACK)

    private var enableDrag = false
    private var isDragging = false
    private var dragCallback: CardDragCallback? = null

    private var enableClick = false
    private var clickCallback: CardInteractCallback? = null


    // 鼠标在卡牌上方
    private var enableMouseOver = false
    private var isMouseOver = false
    private var mouseEnterCallback: CardInteractCallback? = null
    private var mouseLeaveCallback: CardInteractCallback? = null

    override fun onCreate() {
        super.onCreate()
        with(ConstCardSize) {

            darkMaskView.translateX = -card_width_half
            darkMaskView.translateY = -card_height_half
            darkMaskView.isVisible = false

            controlMask.x = -card_width_half
            controlMask.y = -card_height_half
            controlMask.opacity = 0.0


            selected.fitWidth = selected_mark_size
            selected.fitHeight = selected_mark_size
            selected.translateX = -selected_mark_size / 2
            selected.translateY = -selected_mark_size / 2
            selected.isVisible = false

            noSelect.fitWidth = selected_mark_size
            noSelect.fitHeight = selected_mark_size
            noSelect.translateX = -selected_mark_size / 2
            noSelect.translateY = -selected_mark_size / 2
            noSelect.isVisible = false


            cardView.controlGroup.children.addAll(selected, noSelect, darkMaskView, controlMask)




            initMouseEvent(controlMask)
        }
    }

    fun setDark(dark: Boolean = true) {
//        darkMaskView.isVisible = dark
    }

    fun setSelected(selected: Boolean) {
        this.selected.isVisible = selected
    }

    fun setNoSelect(noSelect: Boolean) {
        this.noSelect.isVisible = noSelect
    }


    fun disableAll() {
        enableMouseOver(false)
        enableClick(false)
        enableDrag(false)

        setDark(false)
        setSelected(false)
        setNoSelect(false)
    }

    fun enableClick(enableClick: Boolean = true, clickCallback: CardInteractCallback? = null): ModInteract {
        this.enableClick = enableClick
        this.clickCallback = clickCallback
        this.controlMask.cursor = if (enableClick) Cursor.HAND else Cursor.DEFAULT
        return this
    }

    fun enableDrag(
        enableDrag: Boolean,
        callback: CardDragCallback? = null,
    ): ModInteract {
        this.enableDrag = enableDrag
        if (!enableDrag && isDragging) {
            isDragging = false
            if (callback?.triggerCancel == true) {
                dragCallback?.cancel(cardView) // 触发正在拖拽中的cancel事件
            }
        }
        this.dragCallback = callback
        return this
    }

    fun enableMouseOver(
        enableMouseOver: Boolean,
        enter: CardInteractCallback? = null,
        leave: CardInteractCallback? = null,
    ): ModInteract {
        this.enableMouseOver = enableMouseOver
        if (enter != null) mouseEnterCallback = enter
        if (leave != null) mouseLeaveCallback = leave
        if (!enableMouseOver && isMouseOver) {
            isMouseOver = false
        }
        return this
    }

    override fun update(delta: Double) {
        if (!isDragging) return

        val scale = cardView.modMove.scale.value
        val cardWidthHalf = ViewConfig.CARD_WIDTH * scale / 2.0
        val cardHeightHalf = ViewConfig.CARD_HEIGHT * scale / 2.0
        var fx = Game.getInput().mouseXUI
        var fy = Game.getInput().mouseYUI - cardHeightHalf / 3

        // 靠近屏幕边缘的处理
        fx = clamp(fx, cardWidthHalf, Config.SCREEN_WIDTH - cardWidthHalf)
        fy = clamp(fy, cardHeightHalf, Config.SCREEN_HEIGHT - cardHeightHalf)

        cardView.modMove.move(Point2D(fx, fy), duration = Duration.millis(70.0))

        dragCallback?.update(cardView)
    }

    private fun initMouseEvent(node: Node) {
        node.addEventHandler(MouseDragEvent.MOUSE_PRESSED) {
            if (it.isPrimaryButtonDown && enableDrag && !isDragging) {
                isDragging = true
                dragCallback?.start(cardView)
            }
        }
        node.addEventHandler(MouseDragEvent.MOUSE_RELEASED) {
            if (isDragging) {
                isDragging = false
                dragCallback?.over(cardView)
            }
        }
        node.addEventHandler(MouseEvent.MOUSE_ENTERED) {
            if (!isDragging && enableMouseOver) {
                isMouseOver = true
                mouseEnterCallback?.invoke(cardView)
            }
        }
        node.addEventHandler(MouseEvent.MOUSE_EXITED) {
            if (!isDragging && isMouseOver) {
                isMouseOver = false
                mouseLeaveCallback?.invoke(cardView)
            }
        }
        node.setOnMouseClicked { event: MouseEvent ->
            if (event.button == MouseButton.PRIMARY) {
                if (enableClick) clickCallback?.invoke(cardView)
            } else if (event.button == MouseButton.SECONDARY) {
                if (enableRightClick) rightClickCallback?.invoke(cardView)
            }
        }
    }


    companion object {
        private var enableRightClick = false
        private var rightClickCallback: CardInteractCallback? = null
        fun enableRightClick(enable: Boolean = true, callback: CardInteractCallback? = null) {
            this.enableRightClick = enable
            this.rightClickCallback = callback

//            if (!shape.isBack && cardView.card.confCard.cardType != GameConfig.CARD_TYPE_SUPPORT) {
////                    ViewGroups.cardDetail.show(card)
//                TODO("card detail")
//            }
        }
    }
}