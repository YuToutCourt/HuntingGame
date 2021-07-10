package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.CustomMob.CustomBoss;
import com.wongt8.hunting_game.CustomMob.IronCustomCustom;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AlertCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("alert".equalsIgnoreCase(command.getName()) && sender.isOp()){
            return alert(sender,args);
        }
        return false;
    }


    public boolean alert(CommandSender sender, String[] args){
        if(args.length == 0) return false;
        String message = "§c§l> [SERVER] ";
        for(String letter : args) message += " " + letter;
        Bukkit.broadcastMessage(message);
        Player player = (Player) sender;
        CustomBoss.createBoss(player.getLocation());
        return true;

    }
}
