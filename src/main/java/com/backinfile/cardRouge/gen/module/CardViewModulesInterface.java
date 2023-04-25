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
public interface CardViewModulesInterface {
    CardViewModules getModules();

    default ModView getModView() {
        return getModules().getModView();
    }
    default ModMana getModMana() {
        return getModules().getModMana();
    }
    default ModMove getModMove() {
        return getModules().getModMove();
    }
    default ModInteract getModInteract() {
        return getModules().getModInteract();
    }
}
