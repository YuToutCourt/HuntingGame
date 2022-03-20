package com.wongt8.hunting_game.CustomMob;

import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class CustomBoss implements Listener {


    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        Random rand = new Random();
        if(event.getDamager() instanceof WitherSkull && event.getEntity() instanceof Player){
            int random = rand.nextInt(9);
            if(random == 1 ){ Hunting_Game.WORLD.spawn(event.getDamager().getLocation(),Zombie.class); }

        }
    }

    // CREATE THE CUSTOM BOSS
    public static void createBoss(Location location){
        Wither wither = Hunting_Game.WORLD.spawn(location,Wither.class);
        wither.setCustomName("§cBoss");
        wither.setMaxHealth(200.0);
        wither.setHealth(200.0);

    }
}
