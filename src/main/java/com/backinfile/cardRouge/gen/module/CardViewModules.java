package com.backinfile.cardRouge.gen.module;

import com.backinfile.cardRouge.cardView.CardView;
import com.backinfile.cardRouge.cardView.CardViewBaseMod;
import com.backinfile.cardRouge.cardView.mods.ModInteract;
import com.backinfile.cardRouge.cardView.mods.ModMana;
import com.backinfile.cardRouge.cardView.mods.ModMove;
import com.backinfile.cardRouge.cardView.mods.ModView;

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
        modules[E.ModView.ordinal()] = new ModView(cardView);
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

    public ModView getModView() {
        return (ModView) modules[E.ModView.ordinal()];
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
        ModView,
        ModMana,
        ModMove,
        ModInteract,
    }
}
