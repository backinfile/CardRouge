package com.backinfile.cardRouge.reflection;

import com.backinfile.cardRouge.Config;
import com.backinfile.cardRouge.cardView.CardViewBaseMod;
import com.backinfile.cardRouge.viewGroup.Param;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;

public class GlobalCheck {
    public static void check() {
        var reflection = new Reflections(Config.PACKAGE_NAME);

        checkParamClass(reflection);
    }


    private static void checkParamClass(Reflections reflection) {
        for (var clazz : reflection.getSubTypesOf(Param.class)) {
            try {
                clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Param的子类需要有无参构造函数 class=" + clazz.getName());
            }

        }
    }
}
