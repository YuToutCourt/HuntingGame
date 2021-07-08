package com.wongt8.hunting_game.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RuleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("rule".equalsIgnoreCase(command.getName()) && sender.isOp()){
            return rule(sender,args);
        }
        return false;
    }

    public boolean rule(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.setVelocity(new Vector(0,1,1));
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
