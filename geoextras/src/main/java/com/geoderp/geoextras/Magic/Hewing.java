package com.geoderp.geoextras.Magic;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Hewing implements Listener {
    @EventHandler
    public void onPlayerInteract(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("GeoExtas.magic.enchants.*")) {
            // Has perms
            if (isHewingItem(event.getPlayer().getInventory().getItemInMainHand())) {
                // Block was broken with hewing item
                if (isTree(event.getBlock().getLocation())) {
                    // Block was part of a tree
                    doHew(event.getBlock());
                }
            }
        }
    }

    public boolean isHewingItem(ItemStack item) {
        if (item != null) {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    for (String line : lore) {
                        if (line.equals("§2Hewing")) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public boolean isLeaf(Material mat) {
        return mat.equals(Material.OAK_LEAVES) || mat.equals(Material.BIRCH_LEAVES) || mat.equals(Material.ACACIA_LEAVES) || mat.equals(Material.AZALEA_LEAVES) || mat.equals(Material.CHERRY_LEAVES) || mat.equals(Material.JUNGLE_LEAVES) || mat.equals(Material.SPRUCE_LEAVES) || mat.equals(Material.DARK_OAK_LEAVES) || mat.equals(Material.MANGROVE_LEAVES) || mat.equals(Material.FLOWERING_AZALEA_LEAVES);
    }

    public boolean isTree(Location start) {
        for (int g = 0; g < 33; g++) {
            Material targetMaterial = start.getWorld().getBlockAt(start.getBlockX(), start.getBlockY()+g, start.getBlockZ()).getType();
            if (isLeaf(targetMaterial)) {
                return true;
            }
        }
        return false;
    }

    public void doHew(Block block) {

    }
}
