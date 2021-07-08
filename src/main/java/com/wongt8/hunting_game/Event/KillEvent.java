package com.wongt8.hunting_game.Event;

import com.wongt8.hunting_game.CountPoint.CountPoint;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Collections;
import java.util.UUID;

public class KillEvent implements Listener {

    @EventHandler
    public void onKillMob(EntityDeathEvent event){
        if(!(event.getEntity().getKiller() instanceof Player)) return;
        Player p = event.getEntity().getKiller();
        EntityType mob = event.getEntityType();
        UUID uuidOfPlayer = p.getUniqueId();
        int index = 0;
        for(Player player : Bukkit.getOnlinePlayers()) {
            if (uuidOfPlayer.equals(CountPoint.pointOfEveryone.get(index).getUuid())) {
                CountPoint.pointOfEveryone.get(index).addPts(pointToAdd(mob));
                Collections.sort(CountPoint.pointOfEveryone);
                break;
            }
            index++;
        }

    }

    public int pointToAdd(EntityType mob){
        if(mob.equals(EntityType.RABBIT)){
            return 1;
        }
        if(mob.equals(EntityType.OCELOT)){
            return 2;
        }
        if(mob.equals(EntityType.COW)){
            return 3;
        }
        if(mob.equals(EntityType.PIG)){
            return 5;
        }
        if(mob.equals(EntityType.WOLF)){
            return 15;
        }
        if(mob.equals(EntityType.ZOMBIE)){
            return 30;
        }
        if(mob.equals(EntityType.ENDERMAN)){
            return 50;
        }
        /*
        if(mob.equals(EntityType.RABBIT)){
            return 1;
        }
        if(mob.equals(EntityType.RABBIT)){
            return 1;
        }
        if(mob.equals(EntityType.RABBIT)){
            return 1;
        }
         */
        else {
            return 0;
        }

    }
}
