package com.wongt8.hunting_game.Event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        EntityType mob = event.getDamager().getType();
        if(mob.equals(EntityType.WOLF)){
            event.setDamage(20.0);
        }
    }
}
