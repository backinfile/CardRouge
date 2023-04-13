package com.backinfile.cardRouge.gen.module;

import com.backinfile.cardRouge.cardView.CardView;
import com.backinfile.cardRouge.cardView.CardViewBaseMod;
import com.backinfile.cardRouge.cardView.mods.ModBorder;
import com.backinfile.cardRouge.cardView.mods.ModInteract;
import com.backinfile.cardRouge.cardView.mods.ModMainImage;
import com.backinfile.cardRouge.cardView.mods.ModMove;

/**
 * 此类是自动生成的，不要修改
 */
public interface CardViewModulesInterface {
    CardViewModules getModules();

    default ModMainImage getModMainImage() {
        return getModules().getModMainImage();
    }
    default ModBorder getModBorder() {
        return getModules().getModBorder();
    }
    default ModInteract getModInteract() {
        return getModules().getModInteract();
    }
    default ModMove getModMove() {
        return getModules().getModMove();
    }
}
