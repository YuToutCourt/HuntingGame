package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateSpawnCommand implements CommandExecutor {

    private Hunting_Game main;

    public CreateSpawnCommand(Hunting_Game game) {
        this.main = game;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("createSpawn".equalsIgnoreCase(command.getName()) && sender instanceof Player && sender.isOp()) {
            return createSpawn(sender);
        }

        return false;
    }

    public boolean createSpawn(CommandSender sender){

        Location spawn = this.main.WORLD.getSpawnLocation();
        Player player = (Player) sender;
        GameMode gm = player.getGameMode();
        player.setGameMode(GameMode.CREATIVE);
        player.teleport(spawn);

        String createCube = "fill " + (spawn.getBlockX() - 10) + " " + (spawn.getBlockY() - 1) + " " + (spawn.getBlockZ() - 10);
        createCube += " " + (spawn.getBlockX() + 10) + " " + (spawn.getBlockY() + 4) + " " + (spawn.getBlockZ() + 10);
        createCube += " minecraft:glass";

        String carveCube = "fill " + (spawn.getBlockX() - 9) + " " + (spawn.getBlockY()) + " " + (spawn.getBlockZ() - 9);
        carveCube += " " + (spawn.getBlockX() + 9) + " " + (spawn.getBlockY() + 3) + " " + (spawn.getBlockZ() + 9);
        carveCube += " minecraft:air";

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), createCube);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), carveCube);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.teleport(spawn);
        }

        player.setGameMode(gm);
        return true;
    }
}
