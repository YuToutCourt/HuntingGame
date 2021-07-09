package com.wongt8.hunting_game.Event;

import com.wongt8.hunting_game.Hunting_Game;
import com.wongt8.hunting_game.Tasks.TimerTasks;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvent implements Listener {

    Hunting_Game main;

    public PlayerEvent(Hunting_Game game){
        this.main = game;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage("§7[§3+§7] " + player.getDisplayName());
        this.main.boards.add(this.main.createBoard(player));

        if(!TimerTasks.RUN) {
            player.teleport(this.main.WORLD.getSpawnLocation());
        } else {
            if(!this.main.playersInTheParty.contains(player.getUniqueId()))
                player.setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage("§7[§4-§7] " + player.getDisplayName());
        this.main.removeBoardOf(player);
    }

    @EventHandler
    public void onNetherPortal(PlayerPortalEvent event){
        Player player = event.getPlayer();
        if(event.getCause() != PlayerPortalEvent.TeleportCause.NETHER_PORTAL) return;

        event.setCancelled(true);
        player.sendMessage("§cNether is off");
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event){
        Player playerSender = event.getPlayer();
        String message = event.getMessage();
        event.setFormat(playerSender.getDisplayName() + "§r§7" + " > §r" + message);
    }

}
