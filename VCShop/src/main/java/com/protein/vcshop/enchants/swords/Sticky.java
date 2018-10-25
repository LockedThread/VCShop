package com.protein.vcshop.enchants.swords;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enchants.SwordEnchant;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Sticky extends SwordEnchant {

    private HashMap<Location, Material> previousBlock;

    public Sticky() {
        super("sticky");
        previousBlock = new HashMap<>();
    }

    @Override
    public void execute(Player damager, Player target, int level, double damage) {
        if ( getRandom() <= chances.get(level) ) {
            Block block = target.getLocation().add(0, 1, 0).getBlock();
            if ( !previousBlock.containsKey(block.getLocation()) ) {
                previousBlock.put(block.getLocation(), block.getType());
                block.setType(Material.WEB);
                Bukkit.getScheduler().runTaskLater(VCShop.getInstance(), () -> block.setType(previousBlock.get(block.getLocation())), 150);
            }
        }
    }
}
