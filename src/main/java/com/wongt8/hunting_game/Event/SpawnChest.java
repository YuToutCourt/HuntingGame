package com.wongt8.hunting_game.Event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Chest;

import java.util.Random;

public class SpawnChest {


    public static void chest()
    {
        Random rand = new Random();
        World world = Bukkit.getWorld("world");
        int _coord = (int) world.getWorldBorder().getSize() / 2;
        int x = rand.nextInt(_coord);
        int z = rand.nextInt(_coord);
        int pos_ou_neg = rand.nextInt(4);
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

        int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z);

        Location location = new Location(world, x, y, z);
        location.getBlock().setType(Material.CHEST);
        Chest chest = (Chest) location.getBlock().getState();
        Inventory inventory = chest.getInventory();

        String[] item = {"ENDER_PEARL","EXP_BOTTLE","GOLDEN_APPLE","DIAMOND","SADDLE","BOOKSHELF","FERMENTED_SPIDER_EYE","SPECKLED_MELON","FLINT_AND_STEEL","FLINT","SHEARS","TNT"};
        String[] armor = {"DIAMOND_BOOTS","DIAMOND_LEGGINGS","RABBIT_FOOT","BLAZE_ROD","NETHER_STALK","GHAST_TEAR","MAGMA_CREAM","DIAMOND_HELMET","DIAMOND_CHESTPLATE"};
        String[] arms = {"DIAMOND_SWORD","BOW","DIAMOND_PICKAXE","GOLDEN_CARROT","GLOWSTONE_DUST","STRING","FEATHER",};
        int i = 0;
        int j = rand.nextInt(13);
        int itemOrArmorOrArms = rand.nextInt(3);
        if(j < 4) j += 3;
        while (i <= j)
        {
            if (itemOrArmorOrArms == 0)
            {
                int nb_stack = rand.nextInt(2);
                nb_stack++;
                inventory.addItem(new ItemStack(Material.valueOf(item[rand.nextInt(item.length)]), nb_stack));
                i++;
            }
            else if(itemOrArmorOrArms%2 == 1)
            {
                inventory.addItem(new ItemStack(Material.valueOf(armor[rand.nextInt(armor.length)]), 1));
                int nb_stack = rand.nextInt(2);
                nb_stack++;
                inventory.addItem(new ItemStack(Material.valueOf(item[rand.nextInt(item.length)]), nb_stack));
                i++;
            }
            else if(itemOrArmorOrArms == 2)
            {
                inventory.addItem(new ItemStack(Material.valueOf(arms[rand.nextInt(arms.length)]), 1));
                int nb_stack = rand.nextInt(2);
                nb_stack++;
                inventory.addItem(new ItemStack(Material.valueOf(item[rand.nextInt(item.length)]), nb_stack));
                i++;
            }

        }
        Bukkit.broadcastMessage("§eA §a§lchest §r§ehave just spawned in §fx §6> " + x + ", §fy §6>" + y + ", §fz §6> " + z);
    }
}
