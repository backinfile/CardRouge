package com.backinfile.cardRouge;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ViewConfig {

    public static double CARD_WIDTH = 250;
    public static double CARD_HEIGHT = 350;
    public static final int PILE_SIZE = 50;
    public static final int PILE_EDGE = 40;
    public static final boolean ROTATE_CARD_IN_HAND = false;
    public static final Color BACKGROUND_COLOR = new Color(0.1, 0.1, 0.1, 1);
    public static final Color BACKGROUND_COLOR_OPACITY = new Color(0.1, 0.1, 0.1, 0.5f);
    // 动画时间
    public static final long ANI_CARD_ROTATE_TIME = Time.SEC / 10;
    public static final Duration ANI_CARD_MOVE_TIME = Duration.seconds(0.12);
    public static final Duration ANI_CARD_HOVER_TIME = Duration.seconds(0.05);
    public static final boolean ANI_HAND_CARD_PULSE = true;
    public static final boolean ANI_SELECT_CRYSTAL_PULSE = true;
    public static final double SCALE_HAND_CARD = 0.6;
    public static final double SCALE_HOVER_CARD = 0.9;
    public static final double SCALE_DRAG_CARD = 0.6;
    public static final double SCALE_SLOT_CARD = 0.6;
    public static final double SCALE_DISCOVER_CARD = 0.8;
    public static final double SCALE_DETAIL_CARD = 1;
}
