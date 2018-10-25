package com.protein.vcshop.enchants.bows;

import com.protein.vcshop.enchants.BowEnchant;
import org.bukkit.entity.Player;

public class Lightning extends BowEnchant {

    public Lightning() {
        super("lightning");
    }

    @Override
    public void execute(Player target, int level) {
        if ( getRandom() <= chances.get(level) ) {
            target.getWorld().strikeLightning(target.getLocation());
        }
    }
}
