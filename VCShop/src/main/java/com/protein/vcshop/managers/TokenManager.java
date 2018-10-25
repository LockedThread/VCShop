package com.protein.vcshop.managers;

import com.protein.vcshop.VCShop;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TokenManager extends Manager {

    public TokenManager(VCShop instance) {
        super(instance);
    }

    public void addTokens(UUID uuid, int amount) {
        instance.getUuidTokenMap().put(uuid.toString(), instance.getUuidTokenMap().containsKey(uuid.toString()) ? instance.getUuidTokenMap().get(uuid.toString()) + amount : amount);
    }

    public void addTokens(Player player, int amount) {
        addTokens(player.getUniqueId(), amount);
    }

    public void setTokens(UUID uuid, int amount) {
        instance.getUuidTokenMap().put(uuid.toString(), amount);
    }

    public void setTokens(Player player, int amount) {
        setTokens(player.getUniqueId(), amount);
    }

    public int getTokens(UUID uuid) {
        return instance.getUuidTokenMap().getOrDefault(uuid.toString(), 0);
    }

    public int getTokens(Player player) {
        return getTokens(player.getUniqueId());
    }

    public boolean inMap(UUID uuid) {
        return instance.getUuidTokenMap().containsKey(uuid.toString());
    }

    public boolean inMap(Player player) {
        return inMap(player.getUniqueId());
    }

    public void removeTokens(Player player, int amount) {
        removeTokens(player.getUniqueId(), amount);
    }

    public void removeTokens(UUID uuid, int amount) {
        setTokens(uuid, getTokens(uuid) - amount);
    }
}
