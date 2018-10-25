package com.protein.vcshop.enchants;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enums.ApplicableType;
import fr.galaxyoyo.spigot.nbtapi.ItemStackUtils;
import fr.galaxyoyo.spigot.nbtapi.TagCompound;
import gnu.trove.map.hash.TIntIntHashMap;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enchant {

    public String name;
    public ApplicableType applicableType;
    public int maxLevel;
    public TIntIntHashMap prices;

    public Enchant(String name, ApplicableType applicableType) {
        this.name = name;
        this.applicableType = applicableType;
        this.prices = new TIntIntHashMap();
        VCShop
                .getInstance()
                .getConfig()
                .getConfigurationSection("enchants." + applicableType.name().toLowerCase() + "." + name + ".tokens-prices")
                .getKeys(false).forEach(key -> prices.put(Integer.parseInt(key.replace("level-", "")),
                VCShop.getInstance().getConfig().getInt("enchants." + applicableType.name().toLowerCase() + "." + name + ".tokens-prices." + key)));
    }

    public boolean canAdd(int lvl) {
        return lvl <= maxLevel;
    }

    public ItemStack addEnchant(ItemStack itemStack, int lvl) {
        TagCompound itemTag = ItemStackUtils.getTagCompound(itemStack);
        itemTag.setInt(name, lvl);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if ( meta.hasLore() ) {
            List<String> preLore = new ArrayList<>();
            preLore.add(ChatColor.translateAlternateColorCodes('&', " &6&l* " + VCShop.getInstance().getEnchantManager().decideColor(lvl) + StringUtils.capitalize(name) + " " + VCShop.getInstance().getEnchantManager().toRomanNumerals(lvl)));
            preLore.addAll(meta.getLore());
            meta.setLore(preLore);
            itemStack.setItemMeta(meta);
            return itemStack;
            /*lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                if ( lore.get(i).contains(StringUtils.capitalize(name)) ) {
                    lore.set(i, ChatColor.translateAlternateColorCodes('&', " &6&l* " + VCShop.getInstance().getEnchantManager().decideColor(lvl) + StringUtils.capitalize(name) + " " + VCShop.getInstance().getEnchantManager().toRomanNumerals(lvl)));
                    meta.setLore(lore);
                    itemStack.setItemMeta(meta);
                    return itemStack;
                }
            }*/
        }
        lore.add(ChatColor.translateAlternateColorCodes('&', " &6&l* " + VCShop.getInstance().getEnchantManager().decideColor(lvl) + StringUtils.capitalize(name) + " " + VCShop.getInstance().getEnchantManager().toRomanNumerals(lvl)));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public boolean isApplicable(ItemStack itemStack) {
        return itemStack.getType().name().contains(applicableType.name());
    }

    protected int getRandom() {
        return new Random().nextInt(100) + 1;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Enchant enchant = (Enchant) o;

        return maxLevel == enchant.maxLevel && (name != null ? name.equals(enchant.name) : enchant.name == null) && applicableType == enchant.applicableType;
    }
}
