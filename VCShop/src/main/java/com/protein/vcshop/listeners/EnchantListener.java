package com.protein.vcshop.listeners;

import com.protein.vcshop.VCShop;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EnchantListener implements Listener {

    private VCShop instance;

    public EnchantListener(VCShop instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if ( event.getEntity() instanceof Player && event.getEntity() != event.getDamager() ) {
            Player player = (Player) event.getEntity();
            if ( event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE ) {
                Arrow arrow = (Arrow) event.getDamager();
                if ( arrow.getShooter() instanceof Player ) {
                    instance.getEnchantManager().fireBowEnchants(((Player) arrow.getShooter()).getItemInHand(), player);
                }
            }
            if ( event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK ) {
                if ( event.getDamager() instanceof Player ) {
                    Player damager = (Player) event.getDamager();
                    instance.getEnchantManager().fireSwordEnchants(damager.getItemInHand(), damager, player, event.getDamage());
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if ( !event.isCancelled() && instance.getWorldGuardPlugin().canBuild(event.getPlayer(), event.getBlock()) && event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().hasItemMeta() && event.getPlayer().getItemInHand().getItemMeta().hasLore() ) {
            instance.getEnchantManager().fireToolEnchant(event.getPlayer().getItemInHand(), event.getPlayer(), event.getBlock().getLocation());
        }
    }
}
