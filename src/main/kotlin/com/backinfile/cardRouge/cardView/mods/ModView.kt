package com.backinfile.cardRouge.cardView.mods

import com.almasb.fxgl.core.Updatable
import com.backinfile.cardRouge.GameConfig
import com.backinfile.cardRouge.Res
import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewBaseMod
import com.backinfile.cardRouge.cardView.CardViewModLayer
import com.backinfile.cardRouge.cardView.ConstCardSize
import com.backinfile.cardRouge.view.DescriptionArea
import com.backinfile.support.Time
import com.backinfile.support.fxgl.FXGLUtils
import com.backinfile.support.fxgl.setSize
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.abs

@CardViewModLayer(CardViewModLayer.Layer.Image)
class ModView(cardView: CardView) : CardViewBaseMod(cardView) {
    private val glowView = ImageView()
    private val edgeView = ImageView()
    private val edgeDarkerView = Rectangle(ConstCardSize.inner_width, ConstCardSize.inner_height)

    private val imageView = ImageView()

    private val textGroup = Group()

    private val updater = ConcurrentHashMap<String, Updatable>()

    private val glowViewUpdater = Updatable {
        val curMillis = Time.getCurMillis()
        val pulseTime = 800
        var opacity = abs(curMillis % pulseTime - pulseTime / 2) / (pulseTime / 2f)
        opacity = minOf(0.5f, opacity) + 0.5f
        glowView.opacity = opacity.toDouble()
    }

    override fun onCreate(): Unit = with(ConstCardSize) {
        super.onCreate()

        glowView.image = Res.loadImage(Res.IMG_GLOW_BLUE)
        glowView.fitWidth = card_width + glow_edge_size * 2
        glowView.fitHeight = card_height + glow_edge_size * 2
        glowView.x = -card_width_half - glow_edge_size
        glowView.y = -card_height_half - glow_edge_size
        glowView.isVisible = false
        cardView.controlGroup.children.add(glowView);

        edgeView.image = Res.loadImage(getEdgeByType())
        edgeView.fitWidth = card_width
        edgeView.fitHeight = card_height
        edgeView.x = -card_width_half
        edgeView.y = -card_height_half
        cardView.controlGroup.children.add(edgeView);

        edgeDarkerView.strokeWidth = 2.0
        edgeDarkerView.stroke = STROKE_EDGE_DARK
        edgeDarkerView.fill = Color.TRANSPARENT
        edgeDarkerView.x = -card_width_half + edge_size
        edgeDarkerView.y = -card_height_half + edge_size
        cardView.controlGroup.children.add(edgeDarkerView)


        imageView.image = Res.loadCardImage(cardView.card.confCard, true)
        imageView.fitWidth = inner_width
        imageView.fitHeight = inner_height
        imageView.x = -card_width_half + edge_size
        imageView.y = -card_height_half + edge_size
        cardView.controlGroup.children.add(imageView)


        if (cardView.card.confCard.cardType != GameConfig.CARD_TYPE_SUPPORT) {
            val mask = Rectangle(inner_width, maskHeight + maskHeightOffset)
            mask.x = -card_width_half + edge_size
            mask.y = -card_height_half + card_height - edge_size - maskHeight - maskHeightOffset
            mask.fill = GRADIENT_MASK


            val title = Label(cardView.card.confCard.title)
            title.alignment = Pos.CENTER
            title.setSize(inner_width, title_font_size * 1.5)
            title.translateX = -card_width_half + edge_size
            title.translateY = card_height_half - maskHeight - title_font_size * 1.5
            title.textFill = Color.WHITE
            title.font = FXGLUtils.font(title_font_size, FontWeight.BOLD)
            title.textAlignment = TextAlignment.CENTER


            val description = DescriptionArea(cardView.card.confCard.description)
            description.translateX = 0.0
            description.translateY = card_height_half - maskHeight / 2

            textGroup.children.addAll(mask, description, title)
            cardView.controlGroup.children.add(textGroup)
        }


        val bottomTitle = Label(cardView.card.confCard.title)
        bottomTitle.alignment = Pos.CENTER
        bottomTitle.setPrefSize(inner_width, title_font_size.toDouble())
        bottomTitle.translateX = -card_width_half + edge_size
        bottomTitle.translateY = card_height_half - title_font_size - edge_size * 2 + 1
        bottomTitle.textFill = Color.WHITE
        bottomTitle.font = FXGLUtils.font(title_font_size, FontWeight.BOLD)
        bottomTitle.textAlignment = TextAlignment.CENTER
        bottomTitle.background = BACKGROUND_TITLE

        val subType = Label(cardView.card.confCard.subType)
        subType.alignment = Pos.CENTER
        subType.setMaxSize(inner_width, subType_font_size.toDouble())
        subType.translateX = -card_width_half + edge_size
        subType.translateY = -card_height_half + edge_size
        subType.textFill = Color.WHITE
        subType.font = FXGLUtils.font(subType_font_size, FontWeight.MEDIUM)
        subType.textAlignment = TextAlignment.CENTER
        subType.translateXProperty().bind(subType.widthProperty().multiply(-0.5f).add(0))
        subType.background = BACKGROUND_TITLE
    }

    fun setGlow(glow: Boolean = true, color: Color? = null, pulse: Boolean = true) {
        this.glowView.isVisible = glow
        if (color != null) {
            this.glowView.image
        }

        val animationKey = "setGlow"
        if (glow) {
            if (!pulse) {
                this.glowView.opacity = 1.0
            } else {
                if (!existAnimationUpdater(animationKey)) {
                    setAnimationUpdater(animationKey, this.glowViewUpdater)
                }
            }
        } else {
            setAnimationUpdater(animationKey, null)
        }
    }

    fun setAnimationUpdater(key: String, updatable: Updatable?) {
        if (updatable == null) {
            updater.remove(key)
        } else {
            updater[key] = updatable
        }
    }

    fun existAnimationUpdater(key: String): Boolean {
        return updater.containsKey(key)
    }


    override fun onShapeChange() {
        super.onShapeChange()

        edgeView.isVisible = !cardView.shape.turnBack
        edgeDarkerView.isVisible = !cardView.shape.turnBack

        imageView.image = Res.loadCardImage(cardView.card.confCard, !cardView.shape.turnBack);


        textGroup.isVisible = !cardView.shape.minion && !cardView.shape.turnBack
    }


    private fun getEdgeByType(): String {
        return when (cardView.card.confCard.cardType) {
            GameConfig.CARD_TYPE_UNIT -> Res.IMG_EDGE1
            GameConfig.CARD_TYPE_POWER -> Res.IMG_EDGE4
            GameConfig.CARD_TYPE_ACTION -> Res.IMG_EDGE2
            GameConfig.CARD_TYPE_SUPPORT -> Res.IMG_EDGE3
            else -> Res.IMG_EDGE1
        }
    }

    override fun update(delta: Double) {
        super.update(delta)
        updater.forEach { it.value.onUpdate(delta) }
    }


}