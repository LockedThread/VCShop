package com.protein.vcshop.enchants.pickaxe;

import com.protein.vcshop.enchants.ToolEnchant;
import com.protein.vcshop.enums.ApplicableType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Vision extends ToolEnchant {

    public Vision() {
        super("vision", ApplicableType.PICKAXE);
    }

    @Override
    public void execute(Player player, Location location, int level) {
        if ( getRandom() <= chances.get(level) ) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 80, 0));
        }
    }
}
