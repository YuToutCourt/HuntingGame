package com.wongt8.hunting_game.Tasks;

import com.wongt8.hunting_game.Command.StartCommand;
import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.CustomMob.CustomBoss;
import com.wongt8.hunting_game.CustomMob.IronGolemCustom;
import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.mrmicky.fastboard.FastBoard;

import java.util.*;

public class TimerTasks extends BukkitRunnable {

	public static boolean RUN = false;
	public static int time = 0;
	private boolean boss = false;

	public static int WBtime = 60*60;
	private int WBstate = 0;

	private Hunting_Game main;

	public TimerTasks(Hunting_Game uhc) {
		this.main = uhc;
		time = 0;
	}

	@Override
	public void run() {
		this.updateBoards();
		
		if(RUN) {
			if(time%300 == 0)sendTargetLocation();
			if(time == 10) this.main.WORLD.setDifficulty(Difficulty.NORMAL);
			time ++;
			if(WBstate < 2) WBtime --; // changing wb timer only if not finished
			if(WBtime == 0 && WBstate == 0) { // worldborder starts moving
				this.moveWorldBorder();
				WBstate ++;
			}
			if(WBtime == 0 && WBstate == 1) { // worldborder ends moving
				WBstate ++;
			}
			if (time >= 1200 && time%1200 == 0 && !boss)spawnGolem();

			if(time >= 6000 && !boss){
				this.main.WORLD.playSound(this.main.WORLD.getSpawnLocation(), Sound.WITHER_SPAWN, 1000.0F, 1.0F);
				boss = true;
				int y = this.main.WORLD.getHighestBlockYAt(0, 0);
				Location location = new Location(this.main.WORLD,0,y,0);
				CustomBoss.createBoss(location);
				Bukkit.broadcastMessage("§cThe §lboss §r§cspawned");
			}

		}
		
	}
	
	public static void setRunning(boolean state) {
		RUN = state;
	}

	public static String formatTime(int secs, boolean printHour) {
		ChatColor color = RUN ? ChatColor.YELLOW : ChatColor.RED;
		String ret = "";
		if(!printHour) ret = String.format("%02d:%02d", secs / 60, secs % 60);
		else ret = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);

		return color + ret.replace(":", ChatColor.RESET + ":" + color);
	}

	public static String formatLine(String key, Object value, ChatColor cVal) {
		ChatColor cKey = ChatColor.GOLD;
		ChatColor cRst = ChatColor.RESET;
		return cKey + key + cRst + " > " + cVal + value;
	}

	public static String formatLine(String key, Object value) {
		return formatLine(key, value, ChatColor.YELLOW);
	}

    private void updateBoards() {
        for(FastBoard board : this.main.boards) {
			board.updateLine(1, formatTime(time, true));
			if(CountPoint.getPtsOf(board.getPlayer().getUniqueId()) == 0){
				board.updateLine(3, formatLine("Rank", "§k???"));
				board.updateLine(4, formatLine("Your point","§k???"));
			}
			else{
				board.updateLine(3, formatLine("Rank", CountPoint.getRankOf(board.getPlayer().getUniqueId())));
				board.updateLine(4, formatLine("Your point", CountPoint.getPtsOf(board.getPlayer().getUniqueId())));
			}
			board.updateLine(6, formatLine("Players", this.main.getAlivePlayer()));
			board.updateLine(8, formatLine("Kill", CountPoint.getNbKillOf(board.getPlayer().getUniqueId())));
			board.updateLine(10, formatLine("Border", formatTime(WBtime, false)));
			board.updateLine(11, formatLine("Size", (int)this.main.WORLD.getWorldBorder().getSize()));
        }
	}

	private void sendTargetLocation(){
		for(Map.Entry entry : StartCommand.killerTarget.entrySet()) {
			Player pToSendMessage = Bukkit.getPlayer((UUID) entry.getKey());
			Player pToLocate = Bukkit.getPlayer((UUID) entry.getValue());
			if (pToLocate != null && pToLocate.getGameMode().equals(GameMode.SURVIVAL) && pToSendMessage != null) {
				Location location = pToLocate.getLocation();
				pToSendMessage.sendMessage("§eCoordonate of your target : §c§lx §r§6> " + location.getBlockX() + "§f, §c§ly §r§6> " + location.getBlockY() + "§f, §c§lz §r§6> " + location.getBlockZ());
			}
		}
	}

	private void spawnGolem(){
		Random r = new Random();
		int borderSize = (int) this.main.WORLD.getWorldBorder().getSize();
		for(int i=0;i<this.main.getAlivePlayer();i++){
			int pos_ou_neg = r.nextInt(4);
			int x = r.nextInt(borderSize)/2;
			int z = r.nextInt(borderSize)/2;
			switch (pos_ou_neg){
				case 0:
					x = x * -1;
					z = z * -1;
					break;
				case 1:
					x = x * 1;
					z = z * 1;
					break;
				case 2:
					x = x * -1;
					z = z * 1;
					break;
				case 3:
					x = x * 1;
					z = z * -1;
					break;
			}
			int y = this.main.WORLD.getHighestBlockYAt(x, z);
			Location location = new Location(this.main.WORLD,x,y,z);
			IronGolemCustom.spawnCustomGolem(location);
		}

	}

	private void moveWorldBorder() {
		this.main.WORLD.playSound(this.main.WORLD.getSpawnLocation(), Sound.ANVIL_LAND, 1000.0F, 1.0F);
		List<UUID> listeOfPlayer= new ArrayList<UUID>();
		for(Player player : Bukkit.getOnlinePlayers()){listeOfPlayer.add(player.getUniqueId());}
		int endSize = (listeOfPlayer.size()*100)/4;
		int duration = 1200;
		WBtime = duration;
		this.main.WORLD.getWorldBorder().setSize(endSize, duration);
		Bukkit.broadcastMessage("§c§lBorder is now moving");
	}
}
 