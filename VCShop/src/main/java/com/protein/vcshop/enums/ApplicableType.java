package com.protein.vcshop.enums;

import org.bukkit.inventory.ItemStack;

public enum ApplicableType {
    BOW,
    PICKAXE,
    SPADE,
    SWORD;

    public boolean isApplicable(ItemStack itemStack) {
        return itemStack.getType().name().toLowerCase().contains(name().toLowerCase());
    }
}
