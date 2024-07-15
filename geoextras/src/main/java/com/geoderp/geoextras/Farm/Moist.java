package com.geoderp.geoextras.Farm;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Moist implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.farm.moist")) {
            // Has perms
            if (event.getItem() != null) {
                // Is holding an item
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    // is a left click on a block
                    if (isWet(event.getItem())) {
                        // is holding wet
                        if (event.getClickedBlock() != null) {
                            // target exists
                            doMoist(event.getClickedBlock());
                        }
                    }
                }
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    // is right click on air
                    ItemStack offhand = player.getInventory().getItemInOffHand();
                    if (isWet(event.getItem()) && offhand != null) {
                        // is holding wet and offhand is not empty
                        if (offhand.getType().equals(Material.BUCKET)) {
                            // offhand is bucket
                            offhand.setType(Material.WATER_BUCKET);
                        }
                    }
                }
            }
        }
    }

    public boolean isWet(ItemStack item) {
        if (item.getType().equals(Material.HEART_OF_THE_SEA)) {
            return true;
        }
        return false;
    }

    public void doMoist(Block origin) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Block curr = origin.getRelative(x, 0, z);
                if (curr.getRelative(0,1,0).getType().equals(Material.AIR)) {
                    boolean didSwap = true;
                    switch (curr.getType()) {
                        case LAVA:
                            curr.setType(Material.OBSIDIAN);
                        break;
                        case DIRT:
                            curr.setType(Material.MUD);        
                        break;
                        case WHITE_CONCRETE_POWDER:
                            curr.setType(Material.WHITE_CONCRETE);  
                        break;
                        case LIGHT_GRAY_CONCRETE_POWDER:
                            curr.setType(Material.LIGHT_GRAY_CONCRETE);  
                        break;
                        case GRAY_CONCRETE_POWDER:
                            curr.setType(Material.GRAY_CONCRETE);  
                        break;
                        case BLACK_CONCRETE_POWDER:
                            curr.setType(Material.BLACK_CONCRETE);
                        break;
                        case BROWN_CONCRETE_POWDER:
                            curr.setType(Material.BROWN_CONCRETE);
                        break;
                        case RED_CONCRETE_POWDER:
                            curr.setType(Material.RED_CONCRETE);
                        break;
                        case ORANGE_CONCRETE_POWDER:
                            curr.setType(Material.ORANGE_CONCRETE);
                        break;
                        case YELLOW_CONCRETE_POWDER:
                            curr.setType(Material.YELLOW_CONCRETE);
                        break;
                        case LIME_CONCRETE_POWDER:
                            curr.setType(Material.LIME_CONCRETE);
                        break;
                        case GREEN_CONCRETE_POWDER:
                            curr.setType(Material.GREEN_CONCRETE);                        
                        break;
                        case CYAN_CONCRETE_POWDER:
                            curr.setType(Material.CYAN_CONCRETE);
                        break;
                        case LIGHT_BLUE_CONCRETE_POWDER:
                            curr.setType(Material.LIGHT_BLUE_CONCRETE);
                        break;
                        case BLUE_CONCRETE_POWDER:
                            curr.setType(Material.BLUE_CONCRETE);
                        break;
                        case PURPLE_CONCRETE_POWDER:
                            curr.setType(Material.PURPLE_CONCRETE);
                        break;
                        case MAGENTA_CONCRETE_POWDER:
                            curr.setType(Material.MAGENTA_CONCRETE);
                        break;
                        case PINK_CONCRETE_POWDER:
                            curr.setType(Material.PINK_CONCRETE);
                        break;
                        default:
                            didSwap = false;
                        break;
                    }
                    
                    if (didSwap) {
                        curr.getWorld().spawnParticle(Particle.DRIPPING_WATER, curr.getLocation().add(0.5,0.5,0.5), (int) (15 + Math.random() * 5),0.5,0.5,0.5);
                    }
                }
            }
        }
    }
}
