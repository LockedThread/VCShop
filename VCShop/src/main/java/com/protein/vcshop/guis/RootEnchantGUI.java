package com.protein.vcshop.guis;

import com.protein.lib.gui.Gui;
import com.protein.lib.gui.GuiButton;
import com.protein.vcshop.CustomConfig;
import com.protein.vcshop.ItemStackBuilder;
import com.protein.vcshop.VCShop;
import com.protein.vcshop.enums.ApplicableType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.IntStream;

public class RootEnchantGUI extends Gui {

    private CustomConfig config;

    public RootEnchantGUI(CustomConfig config) {
        super(config.getYamlConfiguration().getString("title"),
                config.getYamlConfiguration().getInt("size"));
        this.config = config;
    }


    @Override
    public void openInventory(Player player) {
        ConfigurationSection items = config.getYamlConfiguration().getConfigurationSection("items");
        items.getKeys(false).forEach(key -> {
            ItemStack itemStack = new ItemStackBuilder(Material.matchMaterial(items.getString(key + ".material")))
                    .setDisplayName(items.getString(key + ".name"))
                    .setLore(items.getStringList(key + ".lore"))
                    .build();
            GuiButton guiButton = new GuiButton(itemStack);
            if ( !items.getStringList(key + ".commands").isEmpty() ) {
                guiButton.setGuiButtonListener(event -> {
                    items.getStringList(key + ".commands").forEach(s -> {
                        if ( s.startsWith("#") ) {
                            String[] words = s.substring(1).split(" ");
                            if ( words[0].equalsIgnoreCase("opengui") ) {
                                if ( words[1].equalsIgnoreCase("enchants") ) {
                                    VCShop.getInstance().openInventory(player, getClass());
                                } else if ( words[1].equalsIgnoreCase("misc") ) {
                                    VCShop.getInstance().openInventory(player, MiscGUI.class);
                                } else if ( words[1].equalsIgnoreCase("tokenshop") ) {
                                    VCShop.getInstance().openInventory(player, RootTokenShopGUI.class);
                                } else if ( words[1].equalsIgnoreCase("sword") && ApplicableType.SWORD.isApplicable(player.getItemInHand()) ) {
                                    new ItemTypeEnchantGUI(player.getItemInHand(), ApplicableType.SWORD).openInventory(player);
                                } else if ( words[1].equalsIgnoreCase("bow") && ApplicableType.BOW.isApplicable(player.getItemInHand()) ) {
                                    new ItemTypeEnchantGUI(player.getItemInHand(), ApplicableType.BOW).openInventory(player);
                                } else if ( words[1].equalsIgnoreCase("pickaxe") && ApplicableType.PICKAXE.isApplicable(player.getItemInHand()) ) {
                                    new ItemTypeEnchantGUI(player.getItemInHand(), ApplicableType.PICKAXE).openInventory(player);
                                } else if ( words[1].equalsIgnoreCase("spade") && ApplicableType.SPADE.isApplicable(player.getItemInHand()) ) {
                                    new ItemTypeEnchantGUI(player.getItemInHand(), ApplicableType.SPADE).openInventory(player);
                                }
                            }
                        } else {
                            player.performCommand(s);
                        }
                    });
                    event.setCancelled(true);
                });
            }
            this.addButtonAt(guiButton, Integer.parseInt(key));
        });
        IntStream.range(0, size).filter(i -> !this.guiButtons.containsKey(i)).forEach(i -> {
            ItemStackBuilder itemStackBuilder = new ItemStackBuilder(Material.matchMaterial(config.getYamlConfiguration().getString("background-item.material")),
                    DyeColor.valueOf(config.getYamlConfiguration().getString("background-item.data").toUpperCase()).getWoolData());
            if ( config.getYamlConfiguration().getBoolean("background-item.enchanted") )
                itemStackBuilder.showEnchanted();
            if ( config.getYamlConfiguration().getString("background-item.name").isEmpty() )
                itemStackBuilder.setDisplayName(" ");
            this.addButtonAt(new GuiButton(itemStackBuilder.build()).setGuiButtonListener(event -> event.setCancelled(true)), i);
        });
        player.openInventory(getInventory());
    }
}
