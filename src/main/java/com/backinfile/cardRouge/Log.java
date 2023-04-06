package com.backinfile.cardRouge;

import com.backinfile.cardRouge.viewGroup.BaseViewGroup;
import kotlin.reflect.KClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class Log {

    public static final Logger task = LoggerFactory.getLogger("task");
    public static final Logger game = LoggerFactory.getLogger("game");
    public static final Logger msg = LoggerFactory.getLogger("msg");
    public static final Logger config = LoggerFactory.getLogger("config");
    public static final Logger board = LoggerFactory.getLogger("board");
    public static final Logger card = LoggerFactory.getLogger("card");
    public static final Logger reflection = LoggerFactory.getLogger("reflection");
    public static final Logger room = LoggerFactory.getLogger("room");
    public static final Logger debug = LoggerFactory.getLogger("debug");
    public static final Logger viewGroup = LoggerFactory.getLogger("viewGroup");
}
