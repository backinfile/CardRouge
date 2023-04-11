package com.backinfile.cardRouge.room

import com.backinfile.cardRouge.Res

enum class RoomType(val iconPath: String, val text: String) {
    Start(Res.IMG_ROOM_ICON_START, "开始"),

    // 开始
    BattleEasy(Res.IMG_ROOM_ICON_BATTLE, "战斗"),
    Battle(Res.IMG_ROOM_ICON_BATTLE, "战斗"),
    BattleElite(Res.IMG_ROOM_ICON_BATTLE_ELITE, "精英战斗"),
    BattleBoss(Res.IMG_ROOM_ICON_BATTLE_ELITE, "boss"),
    Heal(Res.IMG_ROOM_ICON_HEAL, "治疗"),
    SelectCard(Res.IMG_ROOM_ICON_SELECT_CARD, "获得卡牌"),

    // 三选一
    SelectActionCard(Res.IMG_ROOM_ICON_SELECT_CARD, "获得行动牌"),
    SelectUnitCard(Res.IMG_ROOM_ICON_SELECT_CARD, "获取单位牌"),
    SelectPower(Res.IMG_ROOM_ICON_SELECT_POWER, "获得能力"),

    // 选择能力
    SelectHeroPower(Res.IMG_ROOM_ICON_SELECT_POWER, "获得英雄能力"),

    // 选择能力
    ChangeCard(Res.IMG_ROOM_ICON_CHANGE, "交换卡牌"),

    // 交换一张卡（包括复制）
    Shop(Res.IMG_ROOM_ICON_SHOP, "商店"),
    Copy(Res.IMG_ROOM_ICON_COPY, "复制卡牌"),
    Snatch(Res.IMG_ROOM_ICON_SNATCH, "宝藏守卫"),

    // 战斗以抢夺卡牌或能力
    AddonUnitCard(Res.IMG_ROOM_ICON_SELECT_POWER, "单位牌强化"),
    AddonActionCard(Res.IMG_ROOM_ICON_SELECT_POWER, "行动牌强化")

}
