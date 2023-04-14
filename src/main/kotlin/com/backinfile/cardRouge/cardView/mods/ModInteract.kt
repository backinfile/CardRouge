package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.Config
import com.backinfile.cardRouge.Game
import com.backinfile.cardRouge.Log
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewBaseMod
import com.backinfile.support.MathUtils
import javafx.geometry.Point2D
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent
import javafx.util.Duration

private typealias CardInteractCallback = (CardView) -> Unit

class ModInteract(cardView: CardView) : CardViewBaseMod(cardView) {
    private var enableDrag = false
    private var isDragging = false
    private var dragStartCallback: CardInteractCallback? = null
    private var dragUpdateCallback: CardInteractCallback? = null
    private var dragOverCallback: CardInteractCallback? = null
    private var dragCancelCallback: CardInteractCallback? = null

    private var enableClick = false
    private var clickCallback: CardInteractCallback? = null


    // 鼠标在卡牌上方
    private var enableMouseOver = false
    private var isMouseOver = false
    private var mouseEnterCallback: CardInteractCallback? = null
    private var mouseLeaveCallback: CardInteractCallback? = null


    private var isBack = false


    override fun onShape(minion: Boolean, turnBack: Boolean) {
        this.isBack = turnBack
    }

    override fun onCreate() {
        super.onCreate()
        initMouseEvent()
    }

    fun disableAll() {
        enableMouseOver(false)
        enableClick(false)
        enableDrag(false)
    }

    fun enableClick(enableClick: Boolean = true, clickCallback: CardInteractCallback? = null): ModInteract {
        this.enableClick = enableClick
        this.clickCallback = clickCallback
        return this
    }

    fun enableDrag(
        enableDrag: Boolean,
        start: CardInteractCallback? = null,
        update: CardInteractCallback? = null,
        over: CardInteractCallback? = null,
        cancel: CardInteractCallback? = over,
    ): ModInteract {
        this.enableDrag = enableDrag
        dragStartCallback = start
        dragUpdateCallback = update
        dragOverCallback = over
        if (!enableDrag && isDragging) {
            isDragging = false
            dragCancelCallback?.invoke(cardView)
        }

        dragCancelCallback = cancel
        return this
    }

    fun enableMouseOver(
        enableMouseOver: Boolean,
        enter: CardInteractCallback? = null,
        leave: CardInteractCallback? = null,
    ): ModInteract {
        this.enableMouseOver = enableMouseOver
        mouseEnterCallback = enter
        mouseLeaveCallback = leave
        if (!enableMouseOver && isMouseOver) {
            isMouseOver = false
        }
        return this
    }

    override fun update(delta: Double) {
        if (!isDragging) return

        val scale = cardView.modMove.scale.value
        val cardWidthHalf = Config.CARD_WIDTH * scale / 2.0
        val cardHeightHalf = Config.CARD_HEIGHT * scale / 2.0
        var fx = Game.getInput().mouseXUI
        var fy = Game.getInput().mouseYUI - cardHeightHalf / 3

        // 靠近屏幕边缘的处理
        fx = MathUtils.clamp(fx, cardWidthHalf, Config.SCREEN_WIDTH - cardWidthHalf)
        fy = MathUtils.clamp(fy, cardHeightHalf, Config.SCREEN_HEIGHT - cardHeightHalf)

        cardView.modMove.move(Point2D(fx, fy), duration = Duration.millis(70.0))
    }

    private fun initMouseEvent() {
        val controlGroup = cardView.controlGroup
        controlGroup.addEventHandler(MouseDragEvent.MOUSE_PRESSED) {
            if (it.isPrimaryButtonDown && enableDrag && !isDragging) {
                isDragging = true
                dragStartCallback?.invoke(cardView)
            }
        }
        controlGroup.addEventHandler(MouseDragEvent.MOUSE_RELEASED) {
            if (isDragging) {
                isDragging = false
                dragOverCallback?.invoke(cardView)
            }
        }
        controlGroup.addEventHandler(MouseEvent.MOUSE_ENTERED) {
            if (!isDragging && enableMouseOver) {
                isMouseOver = true
                mouseEnterCallback?.invoke(cardView)
            }
        }
        controlGroup.addEventHandler(MouseEvent.MOUSE_EXITED) {
            if (!isDragging && isMouseOver) {
                isMouseOver = false
                mouseLeaveCallback?.invoke(cardView)
            }
        }
        controlGroup.setOnMouseClicked { event: MouseEvent ->
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

//            if (!isBack && cardView.card.confCard.cardType != GameConfig.CARD_TYPE_SUPPORT) {
////                    ViewGroups.cardDetail.show(card)
//                TODO("card detail")
//            }
        }
    }
}