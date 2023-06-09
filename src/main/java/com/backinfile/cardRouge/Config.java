package com.backinfile.cardRouge;

import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;

public class Config {
    public static final boolean DEV = true;

    public static final boolean CARD_MOVE_LOCAL = false; // 卡牌以本地坐标方式移动
    public static final boolean ROBOT_DO_NOT_ACTION = false;
    public static final boolean ROBOT_START_WITH_1_SLOT = false;
    public static final List<Integer> PLAYER_START_WITH_CARDS = List.of(GameConfig.CARD_ID_LAND, GameConfig.CARD_ID_LAND);
//    public static RoomType TEST_ROOM_TYPE = null;

    public static final boolean LOG_GAME_ACTION = true; // 打印action执行信息
    public static final boolean LOG_VIEW_GROUP_SHOW_HIDE = true; // 打印viewGroup显示log


    public static final String PACKAGE_NAME = "com.backinfile.cardRouge";

    public static boolean FULL_SCREEN = false;
    public static int SCREEN_WIDTH = 1280;
    public static int SCREEN_HEIGHT = 768;
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


    public static final double SCALE_HAND_CARD = 0.7;
    public static final double SCALE_HOVER_CARD = 0.9;
    public static final double SCALE_DRAG_CARD = 0.7;
    public static final double SCALE_SLOT_CARD = 0.6;
    public static final double SCALE_DISCOVER_CARD = 0.8;
    public static final double SCALE_DETAIL_CARD = 1;
}
