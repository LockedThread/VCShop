package com.protein.vcshop.placeholders;

import com.protein.vcshop.VCShop;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PAPIHook extends EZPlaceholderHook {

    public PAPIHook(VCShop instance) {
        super(instance, "vcshop");
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        return params.equals("tokens") ? String.valueOf(VCShop.getInstance().getTokenManager().getTokens(player)) : null;
    }
}
