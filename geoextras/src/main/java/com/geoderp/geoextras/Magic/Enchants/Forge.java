package com.geoderp.geoextras.Magic.Enchants;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.geoderp.geoextras.Magic.MagicList;

public class Forge implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.forge")) {
            // has perms
            ItemStack tool = player.getInventory().getItemInMainHand();
            if (isEnchantedItem(tool)) {
                // is forge item
                if (!isQuarryingOrProspecting(tool)) {
                    // is not double working with another enchant
                    if (isForgableBlock(event.getBlock().getType())) {
                        // is a valid forge target
                        event.setDropItems(false);
                        doForge(event.getBlock(), tool);
                    }
                }
            }
        }
    }

    public boolean isEnchantedItem(ItemStack item) {
        return MagicList.getMagicByString("forge").isMagicItem(item);
    }

    public boolean isQuarryingOrProspecting(ItemStack item) {
        return MagicList.getMagicByString("prospecting").isMagicItem(item) || MagicList.getMagicByString("quarrying").isMagicItem(item);
    }

    public static boolean isForgableBlock(Material type) {
        if (type.equals(Material.COPPER_ORE)
            || type.equals(Material.DEEPSLATE_COPPER_ORE)
            || type.equals(Material.IRON_ORE)
            || type.equals(Material.DEEPSLATE_IRON_ORE)
            || type.equals(Material.GOLD_ORE)
            || type.equals(Material.DEEPSLATE_GOLD_ORE)
            || type.equals(Material.ANCIENT_DEBRIS)
            || type.equals(Material.NETHER_GOLD_ORE)) {
            return true;
        }
        return false;
    }

    public static void doForge(Block block, ItemStack tool) {
        if (block.getType().equals(Material.NETHER_GOLD_ORE)) {
            ItemStack drop = new ItemStack(Material.GOLD_INGOT, 1);
            block.getWorld().dropItemNaturally(block.getLocation(), drop);
        }
        else {
            Iterator<ItemStack> drops = block.getDrops(tool).iterator();
            while(drops.hasNext()) {
                ItemStack drop = drops.next();
                ItemStack newDrop;
                Iterator<Recipe> iter = Bukkit.recipeIterator();
                while (iter.hasNext()) {
                    Recipe recipe = iter.next();
                    if (!(recipe instanceof FurnaceRecipe)) 
                        continue;
                    if (((FurnaceRecipe) recipe).getInput().getType() != drop.getType()) 
                        continue;
                    newDrop = recipe.getResult();
                    newDrop.setAmount(drop.getAmount());
                    block.getWorld().dropItemNaturally(block.getLocation(), newDrop);
                    break;
                }
            }
        }

        block.getWorld().spawnParticle(Particle.FLAME, block.getLocation().add(0.5,0.5,0.5), (int) (5 + Math.random() * 5),0.25,0.25,0.25, 0);
    }
}