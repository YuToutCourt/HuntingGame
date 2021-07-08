package com.wongt8.hunting_game.CustomMob;

import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;


public class IronCustomCustom implements Listener {


    // Cancel projectile on the mob
    // Player hit him random effect and little knockback
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Projectile){
            if(event.getEntityType().equals(EntityType.IRON_GOLEM)){
                event.setCancelled(true);
                return;
            }
        }
        if(event.getDamager() instanceof IronGolem){
            Random rand = new Random();
            int chance = rand.nextInt(1);
            if(chance == 0){
                Player player = (Player) event.getEntity();
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,60,2));
            }
        }
    }

    public static void spawnCustomSkeleton(Location location){

        IronGolem golem = Hunting_Game.WORLD.spawn(location,IronGolem.class);
        golem.setCustomName("§k???");
        golem.setMaxHealth(200.0);
        golem.setHealth(200.0);

    }



}
