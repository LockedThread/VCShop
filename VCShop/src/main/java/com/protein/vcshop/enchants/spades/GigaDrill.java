package com.protein.vcshop.enchants.spades;

import com.protein.vcshop.enchants.ToolEnchant;
import com.protein.vcshop.enums.ApplicableType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GigaDrill extends ToolEnchant {

    public GigaDrill() {
        super("gigadrill", ApplicableType.SPADE);
    }

    @Override
    public void execute(Player player, Location location, int level) {

    }
}
