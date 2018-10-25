package com.protein.vcshop.commands;

import com.protein.vcshop.enchants.Enchant;
import com.protein.vcshop.enums.Messages;

public class CmdGiveEnchant extends Command {

    public CmdGiveEnchant() {
        super("giveenchant");
    }

    @Override
    public void run() {
        if ( isPlayer() && sender.hasPermission("tokenshop.giveenchant") && args.length == 2 ) {
            Enchant enchant = instance.getEnchantManager().getEnchant(args[0]);
            if ( enchant == null ) {
                sendMessage(Messages.COMMAND_GIVEENCHANT_NOT_ENCHANT.toString().replace("{enchant}", getArg(0)));
            } else if ( !isInt(getArg(1)) ) {
                sendMessage(Messages.MUST_BE_POSITVE.toString().replace("{num}", getArg(1)));
            } else if ( !enchant.isApplicable(getPlayer().getItemInHand()) ) {
                sendMessage(Messages.COMMAND_GIVEENCHANT_NOT_APPLICABLE.toString());
            } else {
                sendMessage(Messages.COMMAND_GIVEENCHANT_ADDED.toString().replace("{enchant}", getArg(0)).replace("{lvl}", getArg(1)));
                if ( !enchant.canAdd(getArgAsInt(1)) ) {
                    sendMessage(Messages.COMMAND_GIVEENCHANT_ABOVE_MAX.toString().replace("{lvl}", getArg(1)));
                } else {
                    getPlayer().setItemInHand(enchant.addEnchant(getPlayer().getItemInHand(), getArgAsInt(1)));
                }
            }
        }
    }
}
