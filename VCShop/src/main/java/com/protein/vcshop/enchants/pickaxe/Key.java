package com.protein.vcshop.enchants.pickaxe;

import com.protein.vcshop.enchants.ToolEnchant;
import com.protein.vcshop.enums.ApplicableType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Key extends ToolEnchant {

    public Key() {
        super("key", ApplicableType.PICKAXE);
    }

    @Override
    public void execute(Player player, Location location, int level) {

    }
}
