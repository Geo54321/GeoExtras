package com.geoderp.geoextras.Magic;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Forge implements Listener {
    @EventHandler
    public void onPlayerInteract(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("GeoExtas.magic.enchants.*")) {
            ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
            if (isForgeItem(tool)) {
                if (isForgableBlock(event.getBlock().getType())) {
                    event.getPlayer().sendMessage("We're forging now boys");
                    event.setDropItems(false);
                    doForge(event.getBlock(), tool);
                }
            }
        }
    }

    public boolean isForgeItem(ItemStack item) {
        if (item != null) {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    for (String line : lore) {
                        if (line.equals("§6Forge")) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public boolean isForgableBlock(Material type) {
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

    public void doForge(Block block, ItemStack tool) {
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
    }
}