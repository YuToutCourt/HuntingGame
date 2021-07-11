package com.wongt8.hunting_game.Event;

import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CreateSpawnEvent {


    public static void createSpawn(){

        Location spawn = Hunting_Game.WORLD.getSpawnLocation();

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

    }
}
