package com.protein.vcshop.enchants.pickaxe;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enchants.ToolEnchant;
import com.protein.vcshop.enums.ApplicableType;
import gnu.trove.map.hash.TIntIntHashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends ToolEnchant {

    private TIntIntHashMap duration;

    public Haste() {
        super("haste", ApplicableType.PICKAXE);
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
    public void execute(Player player, Location location, int level) {
        if ( getRandom() <= chances.get(level) ) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, duration.get(level) * 20, 0));
        }
    }
}
