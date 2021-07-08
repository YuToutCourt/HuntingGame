package com.wongt8.hunting_game.Event;


import com.wongt8.hunting_game.CountPoint.CountPoint;
import org.bukkit.Bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class WinEvent implements Listener {

    @EventHandler
    public void onKillMob(EntityDeathEvent event){
        for(int i = 0; i< CountPoint.pointOfEveryone.size(); i++){
            if(CountPoint.pointOfEveryone.get(i).getPts() >= 10000){
                Bukkit.broadcastMessage("§aEnd of the game §7§l"+Bukkit.getPlayer(CountPoint.pointOfEveryone.get(i).getUuid()).getName() + "§a win !");
            }
        }
    }
}
