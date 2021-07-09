package com.wongt8.hunting_game.Event;

import com.wongt8.hunting_game.Command.StartCommand;
import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class DeathEvent implements Listener {

    private Hunting_Game main;

    public DeathEvent(Hunting_Game game){
        this.main = game;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Collections.sort(CountPoint.pointOfEveryone);
        Player victim = event.getEntity();
        Player attacker = victim.getKiller();
        Location deathLocation = victim.getLocation();
        victim.spigot().respawn();
        victim.setGameMode(GameMode.SPECTATOR);
        victim.teleport(deathLocation);


        if(!(attacker instanceof Player)){
            event.setDeathMessage("§c§l† §r" + victim.getDisplayName() + " §c§ldied from PVE §c§l†");
            for(Map.Entry entry : StartCommand.killerTarget.entrySet()){
                if(victim.equals(Bukkit.getPlayer((UUID) entry.getValue()))){
                    Player p = Bukkit.getPlayer((UUID) entry.getKey());
                    int classement = CountPoint.getClassementOf((UUID) entry.getKey());
                    CountPoint.pointOfEveryone.get((classement-CountPoint.pointOfEveryone.size())*-1).addPts(100);
                    p.sendMessage("§c§l† §r§aYou target is dead alone ! §r§l+100 pts §c§l††");
                    return;

                }
            }
        }else{
            boolean next = true;
            event.setDeathMessage("§c§l† §r" + victim.getDisplayName()+ " §c§lwas killed by §r" + attacker.getPlayerListName() + " §c§l†");
            int classement = CountPoint.getClassementOf(attacker.getUniqueId());
            CountPoint.pointOfEveryone.get((classement-CountPoint.pointOfEveryone.size())*-1).addKill(1);
            for(Map.Entry entry : StartCommand.killerTarget.entrySet()){
                // KILLER KILL TARGET
                if(attacker.equals(Bukkit.getPlayer((UUID) entry.getKey())) && victim.equals(Bukkit.getPlayer((UUID) entry.getValue()))){
                    CountPoint.pointOfEveryone.get((classement-CountPoint.pointOfEveryone.size())*-1).addPts(500);
                    attacker.sendMessage("§c§l† §r§aYou just killed your target ! §r§l+500 pts §c§l† ");
                    victim.sendMessage("§c§l† §r§4You just died from your killer");
                    this.main.playersInTheParty.remove(victim.getUniqueId());
                    if(this.main.playersInTheParty.size() > 1){
                        Player newTargetPlayer = Bukkit.getPlayer(nextTarget(attacker.getUniqueId()));
                        Location loc = newTargetPlayer.getLocation();
                        attacker.sendMessage("§eCoordonate of your new target : §c§lx §r§6> " + loc.getBlockX() + "§f, §c§ly §r§6> " + loc.getBlockY() + "§f, §c§lz §r§6> " + loc.getBlockZ());
                        StartCommand.killerTarget.replace(attacker.getUniqueId(),newTargetPlayer.getUniqueId());
                    }
                    next = false;
                }
                // TARGET KILL KILLER
                if(attacker.equals(Bukkit.getPlayer((UUID) entry.getValue())) && victim.equals(Bukkit.getPlayer((UUID) entry.getKey()))){
                    CountPoint.pointOfEveryone.get((classement-CountPoint.pointOfEveryone.size())*-1).addPts(100);
                    CountPoint.pointOfEveryone.get((classement-CountPoint.pointOfEveryone.size())*-1).addKillKiller(1);
                    String message = CountPoint.pointOfEveryone.get((classement-CountPoint.pointOfEveryone.size())*-1).getNbKillerKill() <= 7 ? "§c§l† §r§aYou just killed your killer ! You win §r§l+100 pts + permanent bonus" : "§c§l† §aYou just killed your killer ! You win §r§l+100 pts §c§l†";
                    attacker.sendMessage(message);
                    addBonusTo(attacker,classement);
                    victim.sendMessage("§c§l† §r§4You just died from your target §c§l† ");
                    next = false;
                }

            }
            if(next){
                CountPoint.pointOfEveryone.get((classement-CountPoint.pointOfEveryone.size())*-1).addPts(-100);
                attacker.sendMessage("§c§l† §r§4You just killed your someone ! You win §r§l-100 pts §c§l† ");
            }


        }

    }

    public void addBonusTo(Player p,int index){
        int nbKill = (CountPoint.pointOfEveryone.get((index-CountPoint.pointOfEveryone.size())*-1).getNbKillerKill())-1;
        Inventory inv = p.getInventory();
        switch (nbKill){
            case 0:
                inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 8));
                return;
            case 1:
                p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,255555,3));
                return;
            case 2:
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,255555,0));
                return;
            case 3:
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,255555,0));
                return;
            case 4:
                inv.addItem(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
                inv.addItem(new ItemStack(Material.DIAMOND_HELMET, 1));
                return;
            case 5:
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,255555,0));
                return;
            case 6:
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,255555,1));
                return;
            default:
                return;
        }

    }

    public UUID nextTarget(UUID player){
        Collections.shuffle(this.main.playersInTheParty);
        int i = 0;
        while(this.main.playersInTheParty.get(i).equals(player)){
            i++;
        }
        return this.main.playersInTheParty.get(i);
    }
}
