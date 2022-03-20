package com.wongt8.hunting_game.Event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class CutCleanEvent implements Listener {

    @EventHandler
    public void onBreakOre(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material mat = block.getType();
        if(!(mat.equals(Material.GOLD_ORE) || mat.equals(Material.IRON_ORE))) return;
        event.setCancelled(true);
        block.setType(Material.AIR);
        block.getState().update();
        if(mat.equals(Material.GOLD_ORE)) {
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT));
            player.giveExp(4);
        }
        if(mat.equals(Material.IRON_ORE)) {
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT));
            player.giveExp(2);
        }

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        for(ItemStack item : event.getDrops()) {
            switch (item.getType()) {
                case RAW_CHICKEN:
                    item.setType(Material.COOKED_CHICKEN);
                    continue;
                case PORK:
                    item.setType(Material.GRILLED_PORK);
                    continue;
                case RAW_BEEF:
                    item.setType(Material.COOKED_BEEF);
                    continue;
                case MUTTON:
                    item.setType(Material.COOKED_MUTTON);
                    continue;
                case RAW_FISH:
                    item.setType(Material.COOKED_FISH);
                    continue;
                case RABBIT:
                    item.setType(Material.COOKED_RABBIT);
                    continue;
                default:
                    continue;
            }
        }
    }

    @EventHandler
    public void onKillMob(EntityDeathEvent event){
        EntityType mob = event.getEntityType();
        if(mob.equals(EntityType.ZOMBIE)){
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.STRING, 2));
        }
        if(mob.equals(EntityType.HORSE)){
            event.getDrops().clear();
            ItemStack lasagna = new ItemStack(Material.COOKED_BEEF, 1);
            ItemMeta lasagnaMeta = lasagna.getItemMeta();
            lasagnaMeta.setDisplayName("Lasagna");
            lasagna.setItemMeta(lasagnaMeta);
            event.getDrops().add(lasagna);
            event.getDrops().add(new ItemStack(Material.LEATHER, 1));
        }
    }
}
