package com.backinfile.cardRouge.gen.module;

import com.backinfile.cardRouge.cardView.CardView;
import com.backinfile.cardRouge.cardView.CardViewBaseMod;
import com.backinfile.cardRouge.cardView.mods.ModBorder;
import com.backinfile.cardRouge.cardView.mods.ModInteract;
import com.backinfile.cardRouge.cardView.mods.ModMainImage;
import com.backinfile.cardRouge.cardView.mods.ModMana;
import com.backinfile.cardRouge.cardView.mods.ModMove;
import com.backinfile.cardRouge.cardView.mods.ModText;

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
        modules[E.ModText.ordinal()] = new ModText(cardView);
        modules[E.ModMana.ordinal()] = new ModMana(cardView);
        modules[E.ModMove.ordinal()] = new ModMove(cardView);
        modules[E.ModInteract.ordinal()] = new ModInteract(cardView);
    }

    public void onCreate() {
        for (var mod : modules) {
            mod.onCreate();
        }
    }

    public void onShapeChange() {
        for (var mod : modules) {
            mod.onShapeChange();
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
    public ModText getModText() {
        return (ModText) modules[E.ModText.ordinal()];
    }
    public ModMana getModMana() {
        return (ModMana) modules[E.ModMana.ordinal()];
    }
    public ModMove getModMove() {
        return (ModMove) modules[E.ModMove.ordinal()];
    }
    public ModInteract getModInteract() {
        return (ModInteract) modules[E.ModInteract.ordinal()];
    }

    private enum E {
        ModMainImage,
        ModBorder,
        ModText,
        ModMana,
        ModMove,
        ModInteract,
    }
}
