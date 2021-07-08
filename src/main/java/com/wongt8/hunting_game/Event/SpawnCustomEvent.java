package com.wongt8.hunting_game.Event;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SpawnCustomEvent implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        LivingEntity mob = event.getEntity();

        if (EntityType.RABBIT.equals(mob.getType())) {
            mob.setCustomName("§aRabbit");
        }
        if (EntityType.WOLF.equals(mob.getType())) {
            mob.setCustomName("§cWolf");
            mob.damage(15.0);
            mob.setMaxHealth(80.0);
            mob.setHealth(80.0);
        }

    }
}
