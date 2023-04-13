package com.backinfile.cardRouge.reflection;

import com.backinfile.cardRouge.Config;
import com.backinfile.cardRouge.cardView.CardViewBaseMod;
import com.backinfile.cardRouge.cardView.CardViewModLayer;
import javafx.util.Pair;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.*;

public class GenCardViewModules {

    private static final String GEN_PACKAGE = "com.backinfile.cardRouge.gen.module";
    private static final String GEN_PACKAGE_PATH = GEN_PACKAGE.replace('.', '\\');

    private static final String PROJECT_PATH = "src\\main\\java\\";

    public static void main(String[] args) {
        var reflection = new Reflections(Config.PACKAGE_NAME, new SubTypesScanner(false));

        var rootMap = new HashMap<String, Object>();
        rootMap.put("package", GEN_PACKAGE);

        var imports = new ArrayList<String>();
        var classNames = new ArrayList<String>();
        rootMap.put("imports", imports);
        rootMap.put("classes", classNames);

        var classes = new ArrayList<Pair<String, Integer>>();
        for (var clazz : reflection.getSubTypesOf(CardViewBaseMod.class)) {
            imports.add(clazz.getName());

            int priority = CardViewModLayer.Layer.Default.ordinal();

            var annotation = clazz.getAnnotation(CardViewModLayer.class);
            if (annotation != null) {
                priority = annotation.layer().ordinal();
            }
            classes.add(new Pair<>(clazz.getSimpleName(), priority));
        }
        Collections.sort(imports);

        classes.sort(Comparator.<Pair<String, Integer>, Integer>comparing(Pair::getValue).thenComparing(Pair::getKey));
        classNames.addAll(classes.stream().map(Pair::getKey).toList());


        FreeMarkerUtils.formatFile("templates", "CardViewModules.ftl",
                rootMap, PROJECT_PATH + GEN_PACKAGE_PATH, "CardViewModules.java");


        FreeMarkerUtils.formatFile("templates", "CardViewModulesInterface.ftl",
                rootMap, PROJECT_PATH + GEN_PACKAGE_PATH, "CardViewModulesInterface.java");
    }
}
