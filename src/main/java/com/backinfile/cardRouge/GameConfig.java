package com.backinfile.cardRouge;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

// 对战逻辑配置
public class GameConfig {

    public static final int RARE_INIT = 0;
    public static final int RARE_COMMON = 1;
    public static final int RARE_RARE = 2;
    public static final int RARE_BOSS = 3;

    // 职业
    public static final int FAMILY_COMMON = 9;
    public static final int FAMILY_ROBOT = 8;
    public static final int FAMILY_ELEMENT = 1;

    public static final int MANA_MAX_DEFAULT = 3; // 费用上限
    public static final int DRAW_NUMBER_TURN_START = 2; // 回合开始默认抽牌数
    public static final int DRAW_NUMBER_GAME_START = 5; // 起始手牌数
    public static final int HAND_SIZE_DEFAULT_MAX = 10; // 默认手牌上限
    public static final int DEFAULT_SLOT_NUMBER = 5;
    public static final int EASY_SLOT_NUMBER = 3;

    // card type
    public static final int CARD_TYPE_UNIT = 1; // 单位/储备
    public static final int CARD_TYPE_ACTION = 2; // 行动
    public static final int CARD_TYPE_POWER = 3;// 能力
    public static final int CARD_TYPE_SUPPORT = 10; // 辅助卡

    public static final int CARD_ID_CRYSTAL = 100;

    public static final int CARD_ID_WATER = 1100011; // 水元素
    public static final int CARD_ID_FIRE = 1100021; // 火元素
    public static final int CARD_ID_WIND = 1100031; // 风元素
    public static final int CARD_ID_LIGHT = 1100041; // 光元素
    public static final int CARD_ID_THUNDER = 1100051; // 雷元素
    public static final int CARD_ID_DARK = 1100061; // 暗元素
    public static final int CARD_ID_LAND = 1100071; // 地元素
    public static final int CARD_ID_ICE = 1100081; // 冰元素
    public static final int CARD_ID_METAL = 1100091; // 金属元素
    public static final int CARD_ID_LAVA = 1100101; // 熔岩元素
    public static final int CARD_ID_TIME = 1100111; // 时元素
    public static final int CARD_ID_SPACE = 1100121; // 空元素
}
