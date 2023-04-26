package com.backinfile.cardRouge;

// 显示顺序，从前到后
public enum ViewOrder {
    UI_SETTING, // 设置面板
    ICON_SETTING, // 设置按钮
    UI_GROUP_POP_OUT, // 弹出式UI

    UI_ARROW, // 箭头UI

    ZERO,

    CARD_DRAG, // 拖拽中的牌

    UI_OPERATOR, // 可以操作的棋盘上的按钮

    PILE_ICON,
    CARD_HOVER,
    CARD_REVEAL,
    CARD_HAND,
    CARD_BOARD_OVER,
    CARD_BOARD, // 场上的牌
    CARD_BOARD_BACK,
    BACKGROUND,
    ;


    public double order() {
        return (this.ordinal() - ZERO.ordinal()) * 1000.0;
    }
}
