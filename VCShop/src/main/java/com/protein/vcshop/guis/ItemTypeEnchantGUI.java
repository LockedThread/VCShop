package com.protein.vcshop.guis;

import com.protein.lib.gui.Gui;
import com.protein.lib.gui.GuiButton;
import com.protein.vcshop.VCShop;
import com.protein.vcshop.enchants.Enchant;
import com.protein.vcshop.enums.ApplicableType;
import fr.galaxyoyo.spigot.nbtapi.ItemStackUtils;
import fr.galaxyoyo.spigot.nbtapi.TagCompound;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemTypeEnchantGUI extends Gui {

    private ApplicableType applicableType;
    private ItemStack hand;

    public ItemTypeEnchantGUI(ItemStack hand, ApplicableType applicableType) {
        super("&e" + StringUtils.capitalize(applicableType.name().toLowerCase()) + " Enchants", 9);
        this.applicableType = applicableType;
        this.hand = hand;
    }

    @Override
    public void openInventory(Player player) {
        int i = 0;
        ArrayList<Enchant> enchants = new ArrayList<>();
        TagCompound handTagCompound = ItemStackUtils.getTagCompound(hand);

        for (Enchant enchant : VCShop.getInstance().getEnchantManager().getEnchants(hand)) {
            if ( enchant.applicableType == this.applicableType ) {
                for (String s : handTagCompound.keySet()) {
                    Enchant e = VCShop.getInstance().getEnchantManager().getEnchant(s);
                    if ( e != null && e.equals(enchant) && handTagCompound.getInt(s) >= VCShop.getInstance().getEnchantManager().getEnchant(s).maxLevel ) {
                        addButtonAt(new GuiButton(VCShop.getInstance().getGuiMaxLevelEnchantItemStack(s)).setGuiButtonListener(event -> event.setCancelled(true)), i);
                        i++;
                        enchants.add(enchant);
                    }
                }
            }
        }

        for (Enchant enchant : VCShop.getInstance().getEnchantManager().getEnchants()) {
            if ( !contains(enchants, enchant) && enchants.size() <= 9 && enchant.applicableType == this.applicableType ) {
                int level = handTagCompound.containsKey(enchant.name) ? handTagCompound.getInt(enchant.name) + 1 : 1;
                ItemStack itemStack = VCShop.getInstance().getGuiEnchantItemStack(enchant.name, String.valueOf(level));
                TagCompound tagCompound = ItemStackUtils.getTagCompound(itemStack);
                tagCompound.put(enchant.name, level);
                addButtonAt(new GuiButton(itemStack).setGuiButtonListener(event -> {
                    event.setCancelled(true);
                    if ( enchant.prices.get(level) <= VCShop.getInstance().getTokenManager().getTokens(player) ) {
                        enchant.addEnchant(player.getItemInHand(), level);
                        player.closeInventory();
                    }
                }), i);
                i++;
            }
        }

        player.openInventory(getInventory());
    }

    private boolean contains(ArrayList<Enchant> enchants, Enchant enchant) {
        return enchants.stream().anyMatch(e -> e.name.equalsIgnoreCase(enchant.name));
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.size, ChatColor.translateAlternateColorCodes('&', title));
        guiButtons.keySet().forEach(slot -> inventory.setItem(slot, guiButtons.get(slot).getItemStack()));
        return inventory;
    }
}
