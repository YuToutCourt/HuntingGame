package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.Tasks.TimerTasks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("settime".equalsIgnoreCase(command.getName()) && sender.isOp()){
            return time(sender,args);
        }
        return false;
    }


    public boolean time(CommandSender sender, String[] args){
        if(args.length == 0) return false;
        try{
            int time = Integer.parseInt(args[0]);
            TimerTasks.time = time*60;
            TimerTasks.WBtime -= time*60;
            sender.sendMessage("§eThe time have been set to §c"+ args[0]);
            return true;
        }
        catch(Exception e){return false;}
    }
}
