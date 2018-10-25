package com.protein.vcshop.enchants.swords;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enchants.SwordEnchant;
import gnu.trove.map.hash.TIntIntHashMap;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Venom extends SwordEnchant {

    private TIntIntHashMap duration;

    public Venom() {
        super("venom");
        duration = new TIntIntHashMap();
        VCShop
                .getInstance()
                .getConfig()
                .getConfigurationSection("enchants." + applicableType.name().toLowerCase() + "." + name + ".duration")
                .getKeys(false)
                .forEach(key -> duration.put(Integer.parseInt(key.replace("level-", "")), VCShop.getInstance()
                        .getConfig()
                        .getInt("enchants." + applicableType.name().toLowerCase() + "." + name + ".duration." + key)));
    }

    @Override
    public void execute(Player damager, Player target, int level, double damage) {
        if ( getRandom() <= chances.get(level) ) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration.get(level) * 20, 1));
        }
    }
}
