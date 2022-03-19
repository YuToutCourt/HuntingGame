package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
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
        if("target".equalsIgnoreCase(command.getName()) &&  (p.getGameMode().equals(GameMode.SPECTATOR)) || p.getGameMode().equals(GameMode.CREATIVE)){
            return target(sender);
        }
        sender.sendMessage("§cYou need to be in §r§lSPECTATOR §r§cmode to use that command!");
        return false;
    }

    public boolean target(CommandSender sender){
        sender.sendMessage("---- §4Killer -> §2Target §r---- \n");
        for(Map.Entry<UUID,UUID> entry : StartCommand.killerTarget.entrySet()){
            OfflinePlayer killer = Bukkit.getOfflinePlayer((UUID) entry.getKey());
            if(Hunting_Game.playersInTheParty.contains(entry.getKey())){
                OfflinePlayer target = Bukkit.getOfflinePlayer((UUID) entry.getValue());
                if(killer != null && target != null){
                    sender.sendMessage("§c" + killer.getName() + " -> §a" + target.getName());
                }
            }
        }
        return true;

    }
}
