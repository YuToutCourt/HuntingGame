package com.wongt8.hunting_game.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                sender.sendMessage("§9-----------Page 1/?------------");
                return true;
            default:
                return false;

        }
    }

}
