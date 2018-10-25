package com.protein.vcshop.enchants.pickaxe;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enchants.ToolEnchant;
import com.protein.vcshop.enums.ApplicableType;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Explosion extends ToolEnchant {

    public Explosion() {
        super("explosion", ApplicableType.PICKAXE);
    }

    @Override
    public void execute(Player player, Location location, int level) {
        int radius = 2;
        location.getWorld().playEffect(location, Effect.EXPLOSION_LARGE, 0);
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                    Location blockLocation = new Location(location.getWorld(), x, y, z);
                    if ( blockLocation.getBlock().getType() != Material.AIR && blockLocation.getBlock().getType() != Material.BEDROCK && VCShop.getInstance().getWorldGuardPlugin().canBuild(player, blockLocation) ) {
                        if ( ThreadLocalRandom.current().nextInt(0, 100) < chances.get(level) ) {
                            blockLocation.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
