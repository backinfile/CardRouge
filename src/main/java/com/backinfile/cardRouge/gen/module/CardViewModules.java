package com.backinfile.cardRouge.gen.module;

import com.backinfile.cardRouge.cardView.CardView;
import com.backinfile.cardRouge.cardView.CardViewBaseMod;
import com.backinfile.cardRouge.cardView.mods.ModBorder;
import com.backinfile.cardRouge.cardView.mods.ModMainImage;

/**
 * 此类是自动生成的，不要修改
 */
public class CardViewModules {
    private final CardView cardView;
    private CardViewBaseMod[] modules;

    public CardViewModules(CardView cardView) {
        this.cardView = cardView;
    }


    public void init() {
        modules = new CardViewBaseMod[E.values().length];
        modules[E.ModMainImage.ordinal()] = new ModMainImage(cardView);
        modules[E.ModBorder.ordinal()] = new ModBorder(cardView);
    }

    public void onCreate() {
        for (var mod : modules) {
            mod.onCreate();
        }
    }

    public void onShape(boolean minion, boolean turnBack) {
        for (var mod : modules) {
            mod.onShape(minion, turnBack);
        }
    }

    public void update(double delta) {
        for (var mod : modules) {
            mod.update(delta);
        }
    }

    public ModMainImage getModMainImage() {
        return (ModMainImage) modules[E.ModMainImage.ordinal()];
    }
    public ModBorder getModBorder() {
        return (ModBorder) modules[E.ModBorder.ordinal()];
    }

    private enum E {
        ModMainImage,
        ModBorder,
    }
}
