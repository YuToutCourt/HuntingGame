package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Hunting_Game;
import com.wongt8.hunting_game.Tasks.TimerTasks;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

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

        this.main.WORLD.setGameRuleValue("naturalRegeneration", "false");

        Random rand = new Random();

        List<UUID> listeOfPlayer= new ArrayList<UUID>();

        for(Player player : Bukkit.getOnlinePlayers()){listeOfPlayer.add(player.getUniqueId());}

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
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setExp(0);
            Inventory inv = player.getInventory();
            inv.clear();
            this.main.playersInTheParty.add(player.getUniqueId());
            i++;
        }
        this.main.WORLD.setDifficulty(Difficulty.HARD);
        TimerTasks timer = new TimerTasks(this.main);
        timer.runTaskTimer(this.main, 0, 20);
        TimerTasks.setRunning(true);
        return true;

    }
}
