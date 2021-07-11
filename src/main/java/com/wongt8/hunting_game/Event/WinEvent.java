package com.wongt8.hunting_game.Event;


import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Hunting_Game;
import com.wongt8.hunting_game.Tasks.TimerTasks;
import org.bukkit.Bukkit;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class WinEvent implements Listener {

    @EventHandler
    public void onKillMob(EntityDeathEvent event){
        for(int i = 0; i< CountPoint.pointOfEveryone.size(); i++){
            if(CountPoint.pointOfEveryone.get(i).getPts() >= 500*Hunting_Game.setThePointForTheGame.size()){
                Bukkit.broadcastMessage("§aEnd of the game §7§l"+Bukkit.getPlayer(CountPoint.pointOfEveryone.get(i).getUuid()).getName() + "§a win !");
                int y = Hunting_Game.WORLD.getHighestBlockYAt(0,0)+5;
                for(Player p : Bukkit.getOnlinePlayers()){
                    Location spawn = Hunting_Game.WORLD.getSpawnLocation();
                    p.setGameMode(GameMode.SPECTATOR);
                    p.teleport(spawn);
                }
                Hunting_Game.WORLD.playSound(Hunting_Game.WORLD.getSpawnLocation(), Sound.FIREWORK_BLAST, 1000.0F, 1.0F);
                Hunting_Game.WORLD.playSound(Hunting_Game.WORLD.getSpawnLocation(), Sound.FIREWORK_LARGE_BLAST, 1000.0F, 1.0F);
                TimerTasks.setRunning(false);

            }
        }
    }
}
