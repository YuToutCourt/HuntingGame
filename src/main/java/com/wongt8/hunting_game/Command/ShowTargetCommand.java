package com.wongt8.hunting_game.Command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class ShowTargetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if("target".equalsIgnoreCase(command.getName()) && p.getGameMode().equals(GameMode.SPECTATOR)){
            return target(sender);
        }
        sender.sendMessage("§cYou need to be in §r§lSPECTATOR §r§cmode to use that command!");
        return false;
    }

    public boolean target(CommandSender sender){
        sender.sendMessage("---- §4Killer -> §2Target §r---- \n");
        for(Map.Entry<UUID,UUID> entry : StartCommand.killerTarget.entrySet()){
            Player pToSendMessage = Bukkit.getPlayer((UUID) entry.getKey());
            Player pToLocate = Bukkit.getPlayer((UUID) entry.getValue());
            sender.sendMessage("§c" + pToSendMessage.getName() + " -> §a" + pToLocate.getName());
        }
        return true;

    }
}
