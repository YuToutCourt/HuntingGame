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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

            case BAT:
                Bat bat = (Bat) event.getEntity();
                bat.setCustomName("§c§lBat");
                return;

            case CHICKEN:
                Chicken chicken = (Chicken) event.getEntity();
                chicken.setCustomName("§cCHICKEN");
                return;

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

            case SKELETON:
                Skeleton skele = (Skeleton) event.getEntity();
                skele.setCustomName("§cSkeleton");
                skele.setMaxHealth(40.0);
                skele.setHealth(40.0);
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

            case IRON_GOLEM:
                IronGolem golem = (IronGolem) event.getEntity();
                golem.setCustomName("§k???");
                golem.setMaxHealth(200.0);
                golem.setHealth(200.0);
                golem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,255555,2));
                golem.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,255555,2));
                return;

            case HORSE:
                Horse horse = (Horse) event.getEntity();
                horse.setCustomName("§cHORSE");
                horse.setMaxHealth(20.0);
                return;

            // CANCEL SPAWN OF SOME MOBS

            case CREEPER:
            case CAVE_SPIDER:
            case SLIME:
            case SPIDER:
            case GUARDIAN:
            case ENDERMITE:
            case SHEEP:
            case MUSHROOM_COW:
            case SQUID:
            case WITCH:
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
        for(int index = 0; index <CountPoint.pointOfEveryone.size(); index++){
            if (uuidOfPlayer.equals(CountPoint.pointOfEveryone.get(index).getUuid()) && Hunting_Game.playersInTheParty.contains(uuidOfPlayer)){
                int point = pointToAdd(mob);
                if(point < 0) p.sendMessage("§7-§c§l"+ point*-1);
                else p.sendMessage("§7+§a§l"+ point);
                CountPoint.pointOfEveryone.get(index).addPts(point);


                System.out.println("[Hunting Game] Point added successfully !!");
                Collections.sort(CountPoint.pointOfEveryone);
                break;
            }
        }

    }

    public int pointToAdd(EntityType mob){
        switch(mob){
            case RABBIT:
            case CHICKEN:
                return 1;
            case BAT:
                return getRandom(-5,5);
            case OCELOT:
                return 2;
            case COW:
                return 3;
            case PIG:
                return 5;
            case HORSE:
                return 10;
            case WOLF:
                return 15;
            case ZOMBIE:
                return 30;
            case SKELETON:
                return 40;
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
                event.setDamage(1.0);
            case COW:
                event.setDamage(2.0);
            case PIG:
                event.setDamage(3.0);
            case WOLF:
                event.setDamage(4.0);
            case ZOMBIE:
                event.setDamage(7.0);
            case ENDERMAN:
                event.setDamage(7.0);
            default:
                return;

        }

    }

    private int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }
}
