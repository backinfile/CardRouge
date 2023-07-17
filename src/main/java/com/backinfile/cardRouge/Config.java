package com.backinfile.cardRouge;

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


}
