package com.backinfile.cardRouge.cardView.mods

import com.backinfile.cardRouge.cardView.CardView
import com.backinfile.cardRouge.cardView.CardViewBaseMod
import com.backinfile.cardRouge.cardView.CardViewModLayer
import com.backinfile.cardRouge.cardView.ConstCardSize
import com.backinfile.support.fxgl.FXGLUtils
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment


@CardViewModLayer(CardViewModLayer.Layer.TEXT)
class ModText(cardView: CardView) : CardViewBaseMod(cardView) {

    override fun onCreate() {
        super.onCreate()


        val card = cardView.card
        val controlGroup = cardView.controlGroup

        with(ConstCardSize) {
            val title = Label(card.confCard.title)
            title.alignment = Pos.CENTER
            title.setPrefSize(inner_width, title_font_size.toDouble())
            title.translateX = -card_width_half + edge_size
            title.translateY = -card_height_half + card_height - maskHeight
            title.textFill = Color.WHITE
            title.font = FXGLUtils.font(title_font_size, FontWeight.BOLD)
            title.textAlignment = TextAlignment.CENTER

            val description = Label(card.confCard.description.replace("\\n", "\n"))
            description.alignment = Pos.CENTER
            description.setPrefSize(inner_width, maskHeight - title_height)
            description.translateX = -card_width_half + edge_size
            description.translateY =
                -card_height_half + card_height - edge_size - maskHeight + title_height
            description.textFill = Color.WHITE
            description.font = FXGLUtils.font(description_font_size)
            description.textAlignment = TextAlignment.CENTER
            description.isWrapText = true

            val bottomTitle = Label(card.confCard.title)
            bottomTitle.alignment = Pos.CENTER
            bottomTitle.setPrefSize(inner_width, title_font_size.toDouble())
            bottomTitle.translateX = -card_width_half + edge_size
            bottomTitle.translateY = card_height_half - title_font_size - edge_size * 2 + 1
            bottomTitle.textFill = Color.WHITE
            bottomTitle.font = FXGLUtils.font(title_font_size, FontWeight.BOLD)
            bottomTitle.textAlignment = TextAlignment.CENTER
            bottomTitle.background = BACKGROUND_TITLE

            val subType = Label(card.confCard.subType)
            subType.alignment = Pos.CENTER
            subType.setMaxSize(inner_width, subType_font_size.toDouble())
            subType.translateX = -card_width_half + edge_size
            subType.translateY = -card_height_half + edge_size
            subType.textFill = Color.WHITE
            subType.font = FXGLUtils.font(subType_font_size, FontWeight.MEDIUM)
            subType.textAlignment = TextAlignment.CENTER
            subType.translateXProperty().bind(subType.widthProperty().multiply(-0.5f).add(0))
            subType.background = BACKGROUND_TITLE

            val darkMaskView = Rectangle(card_width, card_height, FILL_DARK_MASK)
            darkMaskView.translateX = -card_width_half
            darkMaskView.translateY = -card_height_half


            val mask = Rectangle(inner_width, maskHeight + maskHeightOffset)
            mask.x = -card_width_half + edge_size
            mask.y = -card_height_half + card_height - edge_size - maskHeight - maskHeightOffset
            mask.fill = GRADIENT_MASK

//            controlGroup.children.addAll(title, description, bottomTitle, subType, darkMaskView)
            controlGroup.children.addAll(title, darkMaskView)
        }
    }
}