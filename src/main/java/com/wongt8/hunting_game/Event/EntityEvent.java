package com.wongt8.hunting_game.Event;

import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Hunting_Game;
import com.wongt8.hunting_game.Tasks.TimerTasks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.UUID;

public class EntityEvent implements Listener {

    Hunting_Game main;


    public EntityEvent(Hunting_Game game){
        this.main = game;
    }


    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        EntityType mobs = event.getEntity().getType();

        // CUSTOM MOB

        switch (mobs){
            case RABBIT:
                Rabbit ra = (Rabbit) event.getEntity();
                ra.setCustomName("§cRabbit");
                return;

            case OCELOT:
                Ocelot oc = (Ocelot) event.getEntity();
                oc.setCustomName("§cOCELOT");
                oc.setMaxHealth(25.0);
                oc.setHealth(25.0);
                return;

            case COW:
                Cow cow = (Cow) event.getEntity();
                Hunting_Game.WORLD.spawn(cow.getLocation(),Wolf.class);
                cow.setCustomName("§cCOW");
                cow.setMaxHealth(25.0);
                cow.setHealth(25.0);
                return;

            case PIG:
                Pig pig = (Pig) event.getEntity();
                Hunting_Game.WORLD.spawn(pig.getLocation(),Ocelot.class);
                pig.setCustomName("§cPIG");
                pig.setMaxHealth(30.0);
                pig.setHealth(30.0);
                return;

            case WOLF:
                Wolf wolf = (Wolf) event.getEntity();
                wolf.setCustomName("§cWolf");
                wolf.setMaxHealth(40.0);
                wolf.setHealth(40.0);
                wolf.setAngry(true);
                return;

            case ZOMBIE:
                Zombie zom = (Zombie) event.getEntity();
                zom.setCustomName("§cZOMBIE");
                zom.setMaxHealth(50.0);
                zom.setHealth(50.0);
                zom.getEquipment().setHelmet(new ItemStack(Material.GOLD_HELMET));
                return;

            case ENDERMAN:
                Enderman ender = (Enderman) event.getEntity();
                ender.setCustomName("§cENDERMAN");
                ender.setMaxHealth(80.0);
                ender.setHealth(80.0);
                return;

            // CANCEL SPAWN OF SOME MOBS
            case CHICKEN:
            case CREEPER:
            case CAVE_SPIDER:
            case SLIME:
            case SPIDER:
            case GUARDIAN:
            case ENDERMITE:
            case MUSHROOM_COW:
            case SQUID:
            case SHEEP:
            case WITCH:
            case SKELETON:
            case SILVERFISH:
                event.setCancelled(true);
                return;
            default:
                return;
        }



    }


    @EventHandler
    public void onKillMob(EntityDeathEvent event){
        if(!(TimerTasks.RUN)) return;
        if(!(event.getEntity().getKiller() instanceof Player)) return;
        if(event.getEntity() instanceof Player) return;
        Player p = event.getEntity().getKiller();
        EntityType mob = event.getEntityType();
        UUID uuidOfPlayer = p.getUniqueId();
        if(mob.equals(EntityType.ZOMBIE)){
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.STRING, 2));
        }
        for(int index = 0; index <= Bukkit.getOnlinePlayers().size(); index++){
            if (uuidOfPlayer.equals(CountPoint.pointOfEveryone.get(index).getUuid())) {
                CountPoint.pointOfEveryone.get(index).addPts(pointToAdd(mob));
                p.sendMessage("§7+§a§l"+ pointToAdd(mob));
                Collections.sort(CountPoint.pointOfEveryone);
                break;
            }
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
            case WITHER:
                return 500*Hunting_Game.setThePointForTheGame.size();
            default:
                return 0;
        }

    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        EntityType mob = event.getDamager().getType();

        switch (mob){
            case OCELOT:
                event.setDamage(2.0);
            case COW:
                event.setDamage(3.0);
            case PIG:
                event.setDamage(4.0);
            case WOLF:
                event.setDamage(5.0);
            case ZOMBIE:
                event.setDamage(6.0);
            case ENDERMAN:
                event.setDamage(7.0);
            default:
                return;

        }

    }
}
