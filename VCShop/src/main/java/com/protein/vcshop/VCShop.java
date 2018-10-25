package com.protein.vcshop;

import com.protein.lib.gui.Gui;
import com.protein.lib.gui.GuiListener;
import com.protein.vcshop.commands.CmdGiveEnchant;
import com.protein.vcshop.commands.CmdTokenShop;
import com.protein.vcshop.commands.Command;
import com.protein.vcshop.guis.MiscGUI;
import com.protein.vcshop.guis.RootEnchantGUI;
import com.protein.vcshop.guis.RootTokenShopGUI;
import com.protein.vcshop.listeners.EnchantListener;
import com.protein.vcshop.listeners.TokenListener;
import com.protein.vcshop.managers.EnchantManager;
import com.protein.vcshop.managers.TokenManager;
import com.protein.vcshop.placeholders.PAPIHook;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class VCShop extends JavaPlugin {

    private static VCShop instance;
    private HashMap<String, Integer> uuidTokenMap;
    private EnchantManager enchantManager;
    private TokenManager tokenManager;
    private ArrayList<Command> commands;
    private WorldGuardPlugin worldGuardPlugin;
    private CustomConfig dataFile;
    private CustomConfig rootTokenShopFile;
    private CustomConfig rootEnchantFile;
    private CustomConfig miscFile;
    private ArrayList<Gui> guis;
    private ItemStack guiEnchantItemStack;
    private ItemStack guiMaxLevelEnchantItemStack;
    private ItemStack tokenItemStack;

    public static VCShop getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        this.worldGuardPlugin = setupWorldGuard();
        this.commands = new ArrayList<>(Arrays.asList(new CmdGiveEnchant(), new CmdTokenShop()));
        this.enchantManager = new EnchantManager(this);

        this.rootTokenShopFile = new CustomConfig(this, "tokenshopgui.yml", true);
        this.rootEnchantFile = new CustomConfig(this, "enchantgui.yml", true);
        this.miscFile = new CustomConfig(this, "miscgui.yml", true);
        this.guis = new ArrayList<>(Arrays.asList(new RootTokenShopGUI(rootTokenShopFile), new RootEnchantGUI(rootEnchantFile), new MiscGUI(miscFile)));

        this.tokenManager = new TokenManager(this);

        this.tokenItemStack = new ItemStackBuilder(Material.matchMaterial(getConfig().getString("token-item.material"))).setDisplayName(getConfig().getString("token-item.name")).setLore(getConfig().getStringList("token-item.lore")).build();

        this.guiEnchantItemStack = new ItemStackBuilder(Material.matchMaterial(getConfig().getString("gui-enchant-item.material"))).setDisplayName(getConfig().getString("gui-enchant-item.name")).setLore(getConfig().getStringList("gui-enchant-item.lore")).build();
        this.guiMaxLevelEnchantItemStack = new ItemStackBuilder(Material.matchMaterial(getConfig().getString("gui-max-level-enchant-item.material"))).setDisplayName(getConfig().getString("gui-max-level-enchant-item.name")).setLore(getConfig().getStringList("gui-max-level-enchant-item.lore")).build();

        setupDataFile();
        setupPAPI();

        registerListeners(new EnchantListener(this), new GuiListener(), new TokenListener(instance));
    }

    private void setupDataFile() {
        this.dataFile = new CustomConfig(this, "data.yml", false);
        this.uuidTokenMap = new HashMap<>();

        if ( dataFile.getYamlConfiguration().getConfigurationSection("data") == null ) {
            dataFile.getYamlConfiguration().createSection("data");
            return;
        }

        dataFile.getYamlConfiguration().getConfigurationSection("data").getKeys(false).forEach(data -> uuidTokenMap.put(data, dataFile.getYamlConfiguration().getConfigurationSection("data").getInt(data)));
    }

    @Override
    public void onDisable() {
        ConfigurationSection section = dataFile.getYamlConfiguration().getConfigurationSection("data");
        section.getKeys(false).forEach(key -> section.set(key, null));
        uuidTokenMap.keySet().forEach(key -> section.set(key, uuidTokenMap.get(key)));
        dataFile.save();

        instance = null;
    }

    private void setupPAPI() {
        if ( getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") ) {
            new PAPIHook(this).hook();
        }
    }

    private WorldGuardPlugin setupWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        if ( !(plugin instanceof WorldGuardPlugin) ) {
            getLogger().log(Level.SEVERE, "You must have WorldGuard to run this plugin.");
            getPluginLoader().disablePlugin(this);
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] args) {
        commands.stream().filter(command -> bukkitCommand.getName().equalsIgnoreCase(command.commandName)).forEach(command -> {
            command.init(sender, args);
            command.run();
        });
        return true;
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }
    public TokenManager getTokenManager() {
        return tokenManager;
    }
    public WorldGuardPlugin getWorldGuardPlugin() {
        return worldGuardPlugin;
    }
    public HashMap<String, Integer> getUuidTokenMap() {
        return uuidTokenMap;
    }

    public ItemStack getTokenItemStack() {
        return tokenItemStack;
    }

    public void openInventory(Player player, Class clazz) {
        guis.stream().filter(gui -> gui.getClass().getSimpleName().equals(clazz.getSimpleName())).forEach(gui -> gui.openInventory(player));
    }

    public ItemStack getGuiEnchantItemStack(String enchant, String level) {
        ItemStack itemStack = guiEnchantItemStack.clone();
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', meta.getDisplayName().replace("{enchant}", StringUtils.capitalize(enchant)).replace("{lvl}", level).replace("{price}", String.valueOf(getEnchantManager().getEnchant(enchant).prices.get(Integer.parseInt(level))))));
        meta.setLore(meta.getLore().stream().map(s -> ChatColor.translateAlternateColorCodes('&', s.replace("{enchant}", StringUtils.capitalize(enchant)).replace("{lvl}", level).replace("{price}", String.valueOf(getEnchantManager().getEnchant(enchant).prices.get(Integer.parseInt(level)))))).collect(Collectors.toList()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack getGuiMaxLevelEnchantItemStack(String enchant) {
        ItemStack itemStack = guiMaxLevelEnchantItemStack.clone();
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', meta.getDisplayName().replace("{enchant}", StringUtils.capitalize(enchant))));
        meta.setLore(meta.getLore().stream().map(s -> ChatColor.translateAlternateColorCodes('&', s.replace("{enchant}", StringUtils.capitalize(enchant)))).collect(Collectors.toList()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
