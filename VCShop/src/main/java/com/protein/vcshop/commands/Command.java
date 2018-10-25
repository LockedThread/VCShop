package com.protein.vcshop.commands;

import com.protein.vcshop.VCShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command {

    public String commandName;
    protected VCShop instance = VCShop.getInstance();
    protected CommandSender sender;
    protected String[] args;

    public Command(String commandName) {
        this.commandName = commandName;
    }

    public void init(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    public abstract void run();

    void sendMessage(String s) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    boolean isPlayer() {
        return sender instanceof Player;
    }

    boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    String getArg(int i) {
        return args[i];
    }

    int getArgAsInt(int i) {
        return Integer.parseInt(args[i]);
    }

    double getArgAsDouble(int i) {
        return Double.valueOf(args[i]);
    }

    Player getPlayer() {
        return isPlayer() ? (Player) sender : null;
    }

    Player getTarget(int i) {
        return Bukkit.getPlayer(getArg(i));
    }

}
