package com.protein.lib.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if ( event.getWhoClicked() instanceof Player &&
                event.getClickedInventory() != null &&
                event.getClickedInventory().getHolder() != null &&
                event.getClickedInventory().getHolder() instanceof Gui ) {
            GuiButton button = ((Gui) event.getInventory().getHolder()).getButtonAt(event.getSlot());
            if ( button != null && button.getGuiButtonListener() != null ) {
                button.getGuiButtonListener().onClick(event);
            }
        }
    }
}

