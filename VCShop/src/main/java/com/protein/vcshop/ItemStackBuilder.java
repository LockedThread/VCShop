package com.protein.vcshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemStackBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemStackBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilder(Material material, byte data) {
        itemStack = new ItemStack(material, 1, data);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilder showEnchanted() {
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStackBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore.stream().map(loreBit -> ChatColor.translateAlternateColorCodes('&', loreBit)).collect(Collectors.toCollection(ArrayList::new)));
        return this;
    }

    public ItemStack build() {
        ItemStack clonedStack = itemStack.clone();
        clonedStack.setItemMeta(itemMeta.clone());
        return clonedStack;
    }
}
