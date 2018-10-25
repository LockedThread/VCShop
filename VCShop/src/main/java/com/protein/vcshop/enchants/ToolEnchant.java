package com.protein.vcshop.enchants;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enums.ApplicableType;
import gnu.trove.map.hash.TIntIntHashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class ToolEnchant extends Enchant {

    public TIntIntHashMap chances;

    public ToolEnchant(String name, ApplicableType applicableType) {
        super(name, applicableType);
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

    public abstract void execute(Player player, Location location, int level);
}
