package com.wongt8.hunting_game;

import com.wongt8.hunting_game.Command.*;
import com.wongt8.hunting_game.CustomMob.CustomBoss;
import com.wongt8.hunting_game.CustomMob.IronGolemCustom;
import com.wongt8.hunting_game.Event.*;
import com.wongt8.hunting_game.Tasks.TimerTasks;
import fr.mrmicky.fastboard.FastBoard;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public final class Hunting_Game extends JavaPlugin {

    public final List<FastBoard> boards = new ArrayList<FastBoard>();
    public static final List<UUID> playersInTheParty = new ArrayList<UUID>();
    public static List<UUID> setThePointForTheGame = new ArrayList<UUID>();
    public static World WORLD;

    @Override
    public void onEnable() {
        Bukkit.broadcastMessage("Hunting Game ready");
        this.getCommand("alert").setExecutor(new AlertCommand());
        this.getCommand("rule").setExecutor(new RuleCommand());
        this.getCommand("start").setExecutor(new StartCommand(this));
        this.getCommand("pts").setExecutor(new ShowPtsCommand());
        this.getCommand("target").setExecutor(new ShowTargetCommand());
        this.getCommand("settime").setExecutor(new TimeCommand());

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new EntityEvent(this),this);
        pm.registerEvents(new IronGolemCustom(),this);
        pm.registerEvents(new PlayerEvent(this),this);
        pm.registerEvents(new DeathEvent(this),this);
        pm.registerEvents(new CustomBoss(),this);
        pm.registerEvents(new WinEvent(),this);
        pm.registerEvents(new CutCleanEvent(),this);
        this.resetGame();

    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage("Hunting Game is off");
    }


    public void resetGame() {
        WORLD = Bukkit.getWorld("world");
        WORLD.setSpawnLocation(0, 250, 0);
        WORLD.setPVP(false);

        // Reset scoreboard
        for(FastBoard board : this.boards) {
            board.delete();
        }
        this.boards.clear();
        for(Player p : Bukkit.getOnlinePlayers()) {
            this.boards.add(this.createBoard(p));
        }

    }

    public FastBoard createBoard(Player player) {
        String SEPARATOR = ChatColor.RED + " ";
        FastBoard board = new FastBoard(player);
        board.updateTitle("§2§l༄Hunting§r§aGame");

        List<String> lines = new ArrayList<String>();
        lines.add(SEPARATOR);
        lines.add(TimerTasks.formatTime(0, true));
        lines.add(SEPARATOR);
        lines.add(TimerTasks.formatLine("Rank", "§k???"));
        lines.add(TimerTasks.formatLine("Your point","§k???"));
        lines.add(SEPARATOR);
        lines.add(TimerTasks.formatLine("Kill", 0));
        lines.add(SEPARATOR);
        board.updateLines(lines);

        return board;
    }

    public void removeBoardOf(Player player) {
        for(FastBoard board : this.boards) {
            if(board.getPlayer().getUniqueId() == player.getUniqueId()) {
                board.delete();
                this.boards.remove(board);
                return;
            }
        }
    }

    public int getAlivePlayer(){
        int pAlive = 0;
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getGameMode().equals(GameMode.SURVIVAL)) pAlive++;
        }
        return pAlive;
    }



}
