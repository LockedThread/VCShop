package com.protein.vcshop.enchants;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enums.ApplicableType;
import gnu.trove.map.hash.TIntIntHashMap;
import org.bukkit.entity.Player;

public abstract class SwordEnchant extends Enchant {

    public TIntIntHashMap chances;

    public SwordEnchant(String name) {
        super(name, ApplicableType.SWORD);
        chances = new TIntIntHashMap();
        VCShop
                .getInstance()
                .getConfig()
                .getConfigurationSection("enchants." + applicableType.name().toLowerCase() + "." + name + ".chances")
                .getKeys(false)
                .forEach(key -> chances.put(Integer.parseInt(key.replace("level-", "")), VCShop.getInstance()
                        .getConfig()
                        .getInt("enchants." + applicableType.name().toLowerCase() + "." + name + ".chances." + key)));
        maxLevel = chances.size();
    }

    public abstract void execute(Player damager, Player target, int level, double damage);
}
