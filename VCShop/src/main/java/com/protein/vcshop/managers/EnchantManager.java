package com.protein.vcshop.managers;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enchants.BowEnchant;
import com.protein.vcshop.enchants.Enchant;
import com.protein.vcshop.enchants.SwordEnchant;
import com.protein.vcshop.enchants.ToolEnchant;
import com.protein.vcshop.enchants.bows.Blinding;
import com.protein.vcshop.enchants.bows.Lightning;
import com.protein.vcshop.enchants.bows.Weakened;
import com.protein.vcshop.enchants.bows.Wither;
import com.protein.vcshop.enchants.pickaxe.Explosion;
import com.protein.vcshop.enchants.pickaxe.Haste;
import com.protein.vcshop.enchants.pickaxe.Swift;
import com.protein.vcshop.enchants.pickaxe.Vision;
import com.protein.vcshop.enchants.swords.Freezing;
import com.protein.vcshop.enchants.swords.Lifesteal;
import com.protein.vcshop.enchants.swords.Sticky;
import com.protein.vcshop.enchants.swords.Venom;
import fr.galaxyoyo.spigot.nbtapi.ItemStackUtils;
import fr.galaxyoyo.spigot.nbtapi.TagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EnchantManager extends Manager {

    private ArrayList<Enchant> enchants;
    private HashMap<Integer, String> colorHashMap = new HashMap<>();

    public EnchantManager(VCShop instance) {
        super(instance);
        enchants = new ArrayList<>(Arrays.asList(new Lightning(),
                new Blinding(),
                new Weakened(),
                new Wither(),
                new Freezing(),
                new Lifesteal(),
                new Sticky(),
                new Venom(),
                new Haste(),
                new Vision(),
                new Swift(),
                new Explosion()));
        instance.getConfig().getConfigurationSection("enchant-colors").getKeys(false).forEach(s -> colorHashMap.put(Integer.parseInt(s.replace("level-", "")), instance.getConfig().getString("enchant-colors." + s)));
    }

    public Enchant getEnchant(String name) {
        return enchants.stream().filter(enchant -> enchant.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<Enchant> getEnchants(ItemStack itemStack) {
        ArrayList<Enchant> ench = new ArrayList<>();
        ItemStackUtils.getTagCompound(itemStack).keySet().forEach(key -> enchants.stream().filter(enchant -> key.equals(enchant.name)).forEach(ench::add));
        return ench;
    }

    public HashMap<Enchant, Integer> findEnchantLevels(ItemStack itemStack) {
        HashMap<Enchant, Integer> ench = new HashMap<>();
        TagCompound tagCompound = ItemStackUtils.getTagCompound(itemStack);
        tagCompound.keySet().forEach(s -> enchants.stream().filter(enchant -> s.equals(enchant.name)).forEach(enchant -> ench.put(enchant, tagCompound.getInt(s))));
        return ench;
    }

    public void fireBowEnchants(ItemStack itemStack, Player target) {
        HashMap<Enchant, Integer> ench = findEnchantLevels(itemStack);
        ench.keySet().stream().filter(enchant -> enchant instanceof BowEnchant).forEach(enchant -> ((BowEnchant) enchant).execute(target, ench.get(enchant)));
    }

    public void fireSwordEnchants(ItemStack itemStack, Player damager, Player target, double damage) {
        HashMap<Enchant, Integer> ench = findEnchantLevels(itemStack);
        ench.keySet().stream().filter(enchant -> enchant instanceof SwordEnchant).forEach(enchant -> ((SwordEnchant) enchant).execute(damager, target, ench.get(enchant), damage));
    }

    public void fireToolEnchant(ItemStack itemStack, Player player, Location location) {
        HashMap<Enchant, Integer> ench = findEnchantLevels(itemStack);
        ench.keySet().stream().filter(enchant -> enchant instanceof ToolEnchant).forEach(enchant -> ((ToolEnchant) enchant).execute(player, location, ench.get(enchant)));
    }

    public ChatColor decideColor(int level) {
        return ChatColor.valueOf(colorHashMap.get(level).toUpperCase());
    }

    public String toRomanNumerals(int i) {
        switch (i) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
        }
        return "";
    }

    public ArrayList<Enchant> getEnchants() {
        return enchants;
    }
}
