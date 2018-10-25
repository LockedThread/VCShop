package com.protein.vcshop.listeners;

import com.protein.vcshop.VCShop;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TokenListener implements Listener {

    private VCShop instance;

    public TokenListener(VCShop instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if ( event.getItem() != null &&
                event.getItem().hasItemMeta() &&
                event.getItem().getItemMeta().hasDisplayName() &&
                (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) &&
                instance.getTokenItemStack().getType() == event.getItem().getType() &&
                instance.getTokenItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', event.getItem().getItemMeta().getDisplayName())) ) {
            instance.getTokenManager().addTokens(event.getPlayer(), event.getItem().getAmount());
            event.getPlayer().setItemInHand(null);
        }
    }
}
