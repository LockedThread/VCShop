package com.protein.vcshop.enchants.swords;

import com.protein.vcshop.enchants.SwordEnchant;
import org.bukkit.entity.Player;

public class Lifesteal extends SwordEnchant {

    public Lifesteal() {
        super("lifesteal");
    }

    @Override
    public void execute(Player damager, Player target, int level, double damage) {
        if ( getRandom() <= chances.get(level) ) {
            if ( damager.getHealth() + damage > 20 ) {
                damager.setHealth(20);
            } else {
                damager.setHealth(damager.getHealth() + damage);
            }
        }
    }
}
