package com.wongt8.hunting_game.CustomMob;

import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;


public class IronCustomCustom implements Listener {



    // Cancel projectile on the mob
    // Player hit him random effect and little knockback
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        Random rand = new Random();
        if(event.getDamager() instanceof Projectile){
            if(event.getEntityType().equals(EntityType.IRON_GOLEM)){
                event.setCancelled(true);
                return;
            }
        }
        if(event.getDamager() instanceof IronGolem && event.getEntity() instanceof Player){
            int chance = rand.nextInt(2);
            if(chance == 0){
                Player player = (Player) event.getEntity();
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,80,2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,80,2));
            }
        }
        if(event.getDamager() instanceof Player && event.getEntity() instanceof IronGolem){
            int chance = rand.nextInt(2);
            if(chance == 0){
                Player player = (Player) event.getDamager();
                Location locP = player.getLocation();
                Location locG = event.getEntity().getLocation();
                player.setVelocity(new Vector(locG.getBlockX()-locP.getBlockX(),locG.getBlockY()-locP.getBlockY(),locG.getBlockZ()-locP.getBlockZ()));
            }
        }
    }





    public static void spawnCustomSkeleton(Location location){

        IronGolem golem = Hunting_Game.WORLD.spawn(location,IronGolem.class);
        golem.setCustomName("§k???");
        golem.setMaxHealth(200.0);
        golem.setHealth(200.0);
        golem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,255555,2));

    }



}
