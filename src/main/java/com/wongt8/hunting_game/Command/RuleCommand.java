package com.wongt8.hunting_game.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RuleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("rule".equalsIgnoreCase(command.getName()) && sender.isOp()){
            return rule(sender,args);
        }
        return false;
    }

    public boolean rule(CommandSender sender, String[] args) {
        int page;
        try {
            page = Integer.parseInt(args[0]);
        } catch (Exception e) {
            return false;
        }
        switch (page) {
            case 1:
                sender.sendMessage("§9-----------Page 1/3------------");
                sender.sendMessage("§3Earn §apoints §3by killing prey");
                sender.sendMessage("§3The §2game §3end when a §rplayer §3collects §a10 000 points !");
                sender.sendMessage("§3The key to victory is for all comrades to work together!!");
                return true;
            case 2:
                sender.sendMessage("§9-----------Page 2/3------------");
                sender.sendMessage("§3Rabbit   ➸ §7+§a§l1");
                sender.sendMessage("§3Ocelot   ➸ §7+§a§l2");
                sender.sendMessage("§3Cow      ➸ §7+§a§l3");
                sender.sendMessage("§3Pig      ➸ §7+§a§l5");
                sender.sendMessage("§3Wolf     ➸ §7+§a§l15");
                sender.sendMessage("§3Zombie   ➸ §7+§a§l30");
                sender.sendMessage("§3Enderman ➸ §7+§a§l50");
                sender.sendMessage("§3Human    ➸ §7-§c§l300");
                sender.sendMessage("§k???§3    ➸ §7+§a§l200");
                sender.sendMessage("§3Boss     ➸ §7+§a§l10 000");
                return true;
            case 3:
                sender.sendMessage("§9-----------Page 3/3------------");
                sender.sendMessage("§3Every §rplayer §3have a §ctarget §3that will earn a lot of §apoints");
                sender.sendMessage("§3Some §9rules §3are §e§lhidden §3from the §rplayers\n§3You maybe need to discover them");
                return true;
            default:
                return false;

        }
    }

}
