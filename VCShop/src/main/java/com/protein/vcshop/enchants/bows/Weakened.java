package com.protein.vcshop.enchants.bows;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enchants.BowEnchant;
import gnu.trove.map.hash.TIntIntHashMap;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Weakened extends BowEnchant {

    private TIntIntHashMap duration;

    public Weakened() {
        super("weakened");
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
    public void execute(Player target, int level) {
        if ( getRandom() <= chances.get(level) ) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration.get(level) * 20, 1));
        }
    }
}
