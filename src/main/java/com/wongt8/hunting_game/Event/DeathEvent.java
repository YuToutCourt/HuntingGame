package com.wongt8.hunting_game.Event;

import com.wongt8.hunting_game.Command.StartCommand;
import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.*;
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
        Player victim = event.getEntity();
        Player attacker = victim.getKiller();
        Location deathLocation = victim.getLocation();
        victim.spigot().respawn();
        victim.setGameMode(GameMode.SPECTATOR);
        victim.teleport(deathLocation);
        this.main.WORLD.playSound(deathLocation, Sound.ZOMBIE_REMEDY, 1000.0F, 1.0F);


        if(!(attacker instanceof Player)){
            event.setDeathMessage("§c§l† §r" + victim.getDisplayName() + " §c§ldied from PVE §c§l†");
            // CHECK IF FOR ALL THE PLAYER THAT HAVE THE VICTIM IN TARGET
            for(Map.Entry entry : StartCommand.killerTarget.entrySet()){

                // Find the victim to add point to everyone that is linked to him
                if(victim.equals(Bukkit.getPlayer((UUID) entry.getValue()))){

                    int rank = CountPoint.getRankOf((UUID) entry.getKey());
                    CountPoint.pointOfEveryone.get((rank-CountPoint.pointOfEveryone.size())*-1).addPts(100);

                    Player playerOfTheVictim = Bukkit.getPlayer((UUID) entry.getKey());
                    if(playerOfTheVictim != null)
                        playerOfTheVictim.sendMessage("§c§l† §r§aYou target is dead alone ! §r§l+100 pts §c§l†");


                    this.main.playersInTheParty.remove(victim.getUniqueId());
                    if(this.main.playersInTheParty.size() > 1){
                        Player newTargetPlayer = Bukkit.getPlayer(nextTarget(playerOfTheVictim.getUniqueId()));
                        sendMessageOfNewTarget(playerOfTheVictim,newTargetPlayer);
                    }
                }
            }
        }else{
            boolean doesKillerKilledTheWrongOne = true;

            event.setDeathMessage("§c§l† §r" + victim.getDisplayName()+ " §c§lwas killed by §r" + attacker.getPlayerListName() + " §c§l†");

            int rank = CountPoint.getRankOf(attacker.getUniqueId());
            CountPoint.pointOfEveryone.get((rank - CountPoint.pointOfEveryone.size()) * -1).addKill(1);
            for(Map.Entry entry : StartCommand.killerTarget.entrySet()){
                // KILLER KILL TARGET
                if(attacker.equals(Bukkit.getPlayer((UUID) entry.getKey())) && victim.equals(Bukkit.getPlayer((UUID) entry.getValue()))){
                    CountPoint.pointOfEveryone.get((rank-CountPoint.pointOfEveryone.size())*-1).addPts(500);
                    attacker.sendMessage("§c§l† §r§aYou just killed your target ! §r§l+500 pts §c§l† ");
                    victim.sendMessage("§c§l† §r§4You just died from your killer §c§l†");
                    this.main.playersInTheParty.remove(victim.getUniqueId());
                    if(this.main.playersInTheParty.size() > 1){
                        Player newTargetPlayer = Bukkit.getPlayer(nextTarget(attacker.getUniqueId()));
                        sendMessageOfNewTarget(attacker,newTargetPlayer);
                    }
                    doesKillerKilledTheWrongOne = false;
                }
                // TARGET KILL KILLER
                if(attacker.equals(Bukkit.getPlayer((UUID) entry.getValue())) && victim.equals(Bukkit.getPlayer((UUID) entry.getKey()))){
                    this.main.playersInTheParty.remove(victim.getUniqueId());
                    CountPoint.pointOfEveryone.get((rank-CountPoint.pointOfEveryone.size())*-1).addPts(100);
                    CountPoint.pointOfEveryone.get((rank-CountPoint.pointOfEveryone.size())*-1).addKillKiller(1);
                    String message =  "§c§l† §r§aYou just killed your killer ! You win §r§l+100 pts";
                    if(CountPoint.pointOfEveryone.get((rank-CountPoint.pointOfEveryone.size())*-1).getNbKillerKill() <= 7){
                        message += "+ permanent bonus";
                    }
                    message += "§c§l†";
                    attacker.sendMessage(message);
                    addBonusTo(attacker,rank);
                    victim.sendMessage("§c§l† §r§4You just died from your target §c§l† ");
                    doesKillerKilledTheWrongOne = false;
                }
                if(victim.equals(Bukkit.getPlayer((UUID) entry.getValue())) && Bukkit.getPlayer((UUID) entry.getKey()).getGameMode().equals(GameMode.SURVIVAL) && !attacker.equals(Bukkit.getPlayer((UUID) entry.getKey()))){
                    Player p = Bukkit.getPlayer((UUID)entry.getKey());
                    int rank2 = CountPoint.getRankOf((p.getUniqueId()));
                    CountPoint.pointOfEveryone.get((rank2-CountPoint.pointOfEveryone.size())*-1).addPts(100);
                    p.sendMessage("§c§l† §r§aYou target is dead alone ! §r§l+100 pts §c§l†");
                    this.main.playersInTheParty.remove(victim.getUniqueId());
                    if(this.main.playersInTheParty.size() > 1){
                        Player newTargetPlayer = Bukkit.getPlayer(nextTarget(p.getUniqueId()));
                        sendMessageOfNewTarget(p,newTargetPlayer);
                    }
                }

            }
            if(doesKillerKilledTheWrongOne){
                CountPoint.pointOfEveryone.get((rank-CountPoint.pointOfEveryone.size())*-1).addPts(-300);
                attacker.sendMessage("§c§l† §r§4You just killed your someone ! You win §r§l-300 pts §c§l† ");
            }


        }
        Collections.sort(CountPoint.pointOfEveryone);

    }

    private void addBonusTo(Player p,int index){
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
                inv.addItem(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
                inv.addItem(new ItemStack(Material.DIAMOND_HELMET, 1));
                return;
            case 4:
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,255555,0));
                return;
            case 5:
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,255555,0));
                return;
            case 6:
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,255555,0));
                return;
            default:
                return;
        }

    }

    private UUID nextTarget(UUID player){
        Collections.shuffle(this.main.playersInTheParty);
        int i = 0;
        while(this.main.playersInTheParty.get(i).equals(player) && Bukkit.getOfflinePlayer(this.main.playersInTheParty.get(i)).isOnline()){
            i++;
        }
        return this.main.playersInTheParty.get(i);
    }

    private void sendMessageOfNewTarget(Player player,Player newTarget){
        Location loc = newTarget.getLocation();
        player.sendMessage("§eCoordonate of your new target : §c§lx §r§6> " + loc.getBlockX() + "§f, §c§ly §r§6> " + loc.getBlockY() + "§f, §c§lz §r§6> " + loc.getBlockZ());
        StartCommand.killerTarget.replace(player.getUniqueId(),newTarget.getUniqueId());

    }
}
