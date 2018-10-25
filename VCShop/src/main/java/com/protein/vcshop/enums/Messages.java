package com.protein.vcshop.enums;

import org.bukkit.ChatColor;

public enum Messages {

    NOT_ONLINE("&c{player} is not online!"),
    MUST_BE_POSITVE("&c&l(!) &c{num} must be positive number."),
    CANT_EDIT("&c&l(!) &cYou can't edit terrain here."),

    COMMAND_GIVEENCHANT_NOT_ENCHANT("&c&l(!) &c{enchant} is not an enchant!"),
    COMMAND_GIVEENCHANT_ADDED("&a&l(!) &aAdded &e{enchant} &alevel &e{lvl} &ato your item in your hand!"),
    COMMAND_GIVEENCHANT_NOT_APPLICABLE("&c&l(!) &cThe item in your hand is not applicable to this enchant."),
    COMMAND_GIVEENCHANT_ABOVE_MAX("&c&l(!) &c{lvl} is above the max level for this enchant."),

    COMMAND_TOKENSHOP_GIVE("&a&l(!) &aYou've given &e{player} {tokens} &atokens!"),
    COMMAND_TOKENSHOP_SET("&a&l(!) &aYou've set &e{player}'s &atokens to &e{tokens}&a!"),
    COMMAND_TOKENSHOP_REMOVE("&a&l(!) &aYou've removed &e{tokens} &afrom &e{player}&a!"),
    COMMAND_TOKENSHOP_REMOVE_ERROR("&cError removing tokens from &e{player}&c!"),
    COMMAND_TOKENSHOP_BALANCE("&eTokens: {tokens}"),

    NO_PERMISSION("&c&l(!) &cYou don't have permission to execute this command!"),


    ;

    public String val;

    Messages(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', val);
    }

    public String getKey() {
        return name().toLowerCase().replace("_", "-");
    }

}
