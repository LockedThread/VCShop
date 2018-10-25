package com.protein.vcshop.commands;

import com.protein.vcshop.VCShop;
import com.protein.vcshop.enums.Messages;
import com.protein.vcshop.guis.RootTokenShopGUI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class CmdTokenShop extends Command {

    public CmdTokenShop() {
        super("tokenshop");
    }

    @Override
    public void run() {
        if ( args.length == 0 && isPlayer() ) {
            instance.openInventory(getPlayer(), RootTokenShopGUI.class);
        }
        if ( args.length == 1 ) {
            if ( getArg(0).equalsIgnoreCase("help") ) {
                if ( !sender.hasPermission("tokenshop.help") ) {
                    sendMessage(Messages.NO_PERMISSION.toString());
                    return;
                }
                sendMessage("&e/tokenshop give {player} {tokens}");
                sendMessage("&e/tokenshop set {player} {tokens}");
                sendMessage("&e/tokenshop remove {player} {tokens}");
            }
        }
        if ( args.length <= 2 && args.length > 0 ) {
            if ( getArg(0).equalsIgnoreCase("bal") ) {
                if ( args.length == 1 && isPlayer() ) {
                    sendMessage(Messages.COMMAND_TOKENSHOP_BALANCE.toString().replace("{tokens}", String.valueOf(VCShop.getInstance().getTokenManager().getTokens(getPlayer()))));
                    return;
                }
                Player target = getTarget(1);
                sendMessage(target == null ?
                        Messages.NOT_ONLINE.toString().replace("{player}", getArg(1)) :
                        Messages.COMMAND_TOKENSHOP_BALANCE.toString().replace("{tokens}", String.valueOf(VCShop.getInstance().getTokenManager().getTokens(target))));
            }
            if ( getArg(0).equalsIgnoreCase("withdraw") && isPlayer() ) {
                if ( !isInt(getArg(1)) || getArgAsInt(1) <= 0 ) {
                    sendMessage(Messages.MUST_BE_POSITVE.toString().replace("{num}", getArg(1)));
                } else {
                    final int amount = getArgAsInt(1);
                    final int playerTokens = instance.getTokenManager().getTokens(getPlayer());
                    if ( playerTokens < amount || Arrays.stream(getPlayer().getInventory().getContents()).filter(Objects::isNull).mapToInt(itemStack -> 64).sum() < amount ) {
                        sendMessage(Messages.COMMAND_TOKENSHOP_REMOVE_ERROR.toString().replace("{player}", getPlayer().getName()));
                    } else {
                        instance.getTokenManager().removeTokens(getPlayer(), amount);
                        sendMessage(Messages.COMMAND_TOKENSHOP_REMOVE.toString().replace("{player}", getPlayer().getName()).replace("{tokens}", String.valueOf(amount)));
                        IntStream.range(0, amount).forEach(i -> getPlayer().getInventory().addItem(VCShop.getInstance().getTokenItemStack()));
                    }
                }
            }
        }
        if ( args.length == 3 ) {
            if ( getArg(0).equalsIgnoreCase("give") ) {
                if ( !sender.hasPermission("tokenshop.give") ) {
                    sendMessage(Messages.NO_PERMISSION.toString());
                    return;
                }
                Player target = getTarget(1);
                if ( target == null ) {
                    sendMessage(Messages.NOT_ONLINE.toString().replace("{player}", getArg(1)));
                } else if ( !isInt(getArg(2)) || getArgAsInt(2) <= 0 ) {
                    sendMessage(Messages.MUST_BE_POSITVE.toString().replace("{num}", getArg(2)));
                } else {
                    final int amount = getArgAsInt(2);
                    instance.getTokenManager().addTokens(target, amount);
                    sendMessage(Messages.COMMAND_TOKENSHOP_GIVE.toString().replace("{player}", target.getName()).replace("{tokens}", String.valueOf(amount)));
                }
            } else if ( getArg(0).equalsIgnoreCase("set") ) {
                if ( !sender.hasPermission("tokenshop.set") ) {
                    sendMessage(Messages.NO_PERMISSION.toString());
                    return;
                }
                Player target = getTarget(1);
                if ( target == null ) {
                    sendMessage(Messages.NOT_ONLINE.toString().replace("{player}", getArg(1)));
                } else if ( !isInt(getArg(2)) || getArgAsInt(2) <= 0 ) {
                    sendMessage(Messages.MUST_BE_POSITVE.toString().replace("{num}", getArg(2)));
                } else {
                    final int amount = getArgAsInt(2);
                    instance.getTokenManager().setTokens(target, amount);
                    sendMessage(Messages.COMMAND_TOKENSHOP_SET.toString().replace("{player}", target.getName()).replace("{tokens}", String.valueOf(amount)));
                }
            } else if ( getArg(0).equalsIgnoreCase("remove") ) {
                if ( !sender.hasPermission("tokenshop.remove") ) {
                    sendMessage(Messages.NO_PERMISSION.toString());
                    return;
                }
                Player target = getTarget(1);
                if ( target == null ) {
                    sendMessage(Messages.NOT_ONLINE.toString().replace("{player}", getArg(1)));
                } else if ( !isInt(getArg(2)) || getArgAsInt(2) <= 0 ) {
                    sendMessage(Messages.MUST_BE_POSITVE.toString().replace("{num}", getArg(2)));
                } else {
                    final int amount = getArgAsInt(2);
                    final int playerTokens = instance.getTokenManager().getTokens(target);
                    if ( playerTokens < amount ) {
                        sendMessage(Messages.COMMAND_TOKENSHOP_REMOVE_ERROR.toString().replace("{player}", target.getName()));
                    } else {
                        instance.getTokenManager().removeTokens(target, amount);
                        sendMessage(Messages.COMMAND_TOKENSHOP_REMOVE.toString().replace("{player}", target.getName()).replace("{tokens}", String.valueOf(amount)));
                    }
                }
            }
        }
    }
}
