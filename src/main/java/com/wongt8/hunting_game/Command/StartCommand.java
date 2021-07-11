package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Hunting_Game;
import com.wongt8.hunting_game.Tasks.TimerTasks;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class StartCommand implements CommandExecutor {

    public static Map<UUID,UUID> killerTarget = new HashMap<UUID,UUID>();

    private Hunting_Game main;

    public StartCommand(Hunting_Game game) {
        this.main = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("start".equalsIgnoreCase(command.getName()) && sender.isOp()){
            return start();
        }
        return false;
    }


    public boolean start(){

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams remove pseudo");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams add pseudo");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option pseudo nametagVisibility never");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option pseudo seeFriendlyInvisibles false");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join pseudo @a");

        this.main.WORLD.setGameRuleValue("naturalRegeneration", "true");
        this.main.WORLD.setPVP(true);

        Random rand = new Random();
        List<UUID> listeOfPlayer= new ArrayList<UUID>();
        for(Player player : Bukkit.getOnlinePlayers()){listeOfPlayer.add(player.getUniqueId());}

        this.main.WORLD.setDifficulty(Difficulty.PEACEFUL);
        this.main.WORLD.setTime(0);
        this.main.WORLD.getWorldBorder().setCenter(this.main.WORLD.getSpawnLocation());
        this.main.WORLD.getWorldBorder().setSize(listeOfPlayer.size()*100);
        int borderSize = (listeOfPlayer.size()*100)/2;


        //TO AVOID PP SPAWN AT THE TOP OF THE SPAWN
        Location spawn = this.main.WORLD.getSpawnLocation();
        String  removeCube = "fill " + (spawn.getBlockX() - 10) + " " + (spawn.getBlockY() - 1) + " " + (spawn.getBlockZ() - 10);
        removeCube += " " + (spawn.getBlockX() + 10) + " " + (spawn.getBlockY() + 4) + " " + (spawn.getBlockZ() + 10);
        removeCube += " minecraft:air";
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), removeCube);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spreadplayers 0 0 " + borderSize / 10 + " "+ (borderSize - 10) +" false @a"); //x z DistanceEntreChaquePlayer MaxRangeSurLaTp team?


        Collections.shuffle(listeOfPlayer);
        int i = 0;
        for(Player player : Bukkit.getOnlinePlayers()){

            killerTarget.put(listeOfPlayer.get(i),listeOfPlayer.get((i+1)%listeOfPlayer.size()));

            CountPoint.pointOfEveryone.add(new CountPoint(player.getUniqueId(),0));

            // reset potion effects
            for(PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }

            // reset achievements
            for(Achievement a : Achievement.values()) {
                if(player.hasAchievement(a)) player.removeAchievement(a);
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*60, 255));

            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setExp(0);
            Inventory inv = player.getInventory();
            inv.clear();
            inv.addItem(new ItemStack(Material.COOKED_BEEF, 64));
            this.main.playersInTheParty.add(player.getUniqueId());
            this.main.setThePointForTheGame.add(player.getUniqueId());
            i++;
        }
        TimerTasks timer = new TimerTasks(this.main);
        timer.runTaskTimer(this.main, 0, 20);
        TimerTasks.setRunning(true);
        for(int j = 0; j < 100; j++) Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage("§l> [SERVER] §cSetting up the game start... ");
        return true;

    }
}
