package com.wongt8.hunting_game.Event;

import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Hunting_Game;
import com.wongt8.hunting_game.Tasks.TimerTasks;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.UUID;

public class EntityEvent implements Listener {

    Hunting_Game main;


    public EntityEvent(Hunting_Game game){
        this.main = game;
    }


    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        LivingEntity mobs = event.getEntity();

        // CUSTOM MOB
        //TODO Repalce with SWITCH
        if (EntityType.RABBIT.equals(mobs.getType())){
            Rabbit mob = (Rabbit) mobs;
            mob.setCustomName("§cRabbit");
        }
        if(EntityType.OCELOT.equals(mobs.getType())){
            Ocelot mob = (Ocelot) mobs;
            mob.setCustomName("§cOCELOT");
            mob.setMaxHealth(25.0);
            mob.setHealth(25.0);
        }
        if(EntityType.COW.equals(mobs.getType())){
            Cow mob = (Cow) mobs;
            mob.setCustomName("§cCOW");
            mob.setMaxHealth(25.0);
            mob.setHealth(25.0);
        }
        if(EntityType.PIG.equals(mobs.getType())){
            Pig mob = (Pig) mobs;
            mob.setCustomName("§cPIG");
            mob.setMaxHealth(30.0);
            mob.setHealth(30.0);
        }
        if(EntityType.WOLF.equals(mobs.getType())){
            Wolf mob = (Wolf) mobs;
            mob.setCustomName("§cWolf");
            mob.setMaxHealth(40.0);
            mob.setHealth(40.0);
        }
        if(EntityType.ZOMBIE.equals(mobs.getType())){
            Zombie mob = (Zombie) mobs;
            mob.setCustomName("§cZOMBIE");
            mob.setMaxHealth(60.0);
            mob.setHealth(60.0);
        }
        if(EntityType.ENDERMAN.equals(mobs.getType())){
            Enderman mob = (Enderman) mobs;
            mob.setCustomName("§cENDERMAN");
            mob.setMaxHealth(80.0);
            mob.setHealth(80.0);
        }

        // CANCEL SPAWN OF SOME MOBS

        if(EntityType.CHICKEN.equals(mobs.getType())){
            event.setCancelled(true);
        }
        else if(EntityType.IRON_GOLEM.equals(mobs.getType())) {
            BukkitRunnable task = new BukkitRunnable(){
                @Override
                public void run() {
                    if(mobs.getCustomName() == null){
                        mobs.setHealth(0);
                    }
                }
            };
            task.runTaskLater(this.main,1);
        }

    }


    @EventHandler
    public void onKillMob(EntityDeathEvent event){
        if(!(TimerTasks.RUN)) return;
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
        switch(mob){
            case RABBIT:
                return 1;
            case OCELOT:
                return 2;
            case COW:
                return 3;
            case PIG:
                return 5;
            case WOLF:
                return 15;
            case ZOMBIE:
                return 30;
            case ENDERMAN:
                return 50;
            case IRON_GOLEM:
                return 200;
            case SKELETON:
                return 10000;
            default:
                return 0;
        }

    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        EntityType mob = event.getDamager().getType();

        //TODO Repalce with SWITCH
        if(mob.equals(EntityType.OCELOT)){
            event.setDamage(2.0);
        }
        if(mob.equals(EntityType.COW)){
            event.setDamage(3.0);
        }
        if(mob.equals(EntityType.PIG)){
            event.setDamage(4.0);
        }
        if(mob.equals(EntityType.WOLF)){
            event.setDamage(5.0);
        }
        if(mob.equals(EntityType.ZOMBIE)){
            event.setDamage(6.0);
        }
        if(mob.equals(EntityType.ENDERMAN)){
            event.setDamage(7.0);
        }
        /*
        if(mob.equals(EntityType.WOLF)){
            event.setDamage(5.0);
        }
        if(mob.equals(EntityType.WOLF)){
            event.setDamage(5.0);
         */
    }
}
