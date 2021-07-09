package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.CustomMob.IronCustomCustom;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class ShowTargetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("target".equalsIgnoreCase(command.getName()) && sender.isOp()){
            return target();
        }
        return false;
    }

    public boolean target(){
        for(Map.Entry entry : StartCommand.killerTarget.entrySet()){
            Player pToSendMessage = Bukkit.getPlayer((UUID) entry.getKey());
            Player pToLocate = Bukkit.getPlayer((UUID) entry.getValue());
            Bukkit.broadcastMessage("§aKiller = "+ pToSendMessage.getName() + " §cTarget = " + pToLocate.getName());
        }
        return true;

    }
}
