package com.geoderp.geoextras.Magic;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Illumination implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.illumination")) {
            // has perms
            if (isIlluminationItem(player.getInventory().getItemInOffHand())) {
                // offhand item is an illumination item
                if (isDark(player)) {
                    // is in complete darkness
                    if (hasTorches(player) && isPlaceableFloor(player)) {
                        // player has torches and the floor is solid and has air above it
                        illuminate(player);
                    }
                }
            }
        }
    }

    public boolean isIlluminationItem(ItemStack item) {
        if (item != null) {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    for (String line : lore) {
                        if (line.equals(MagicList.getMagicByString("illumination").getLore())) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public boolean isDark(Player player) {
        Block floor = player.getLocation().getBlock().getRelative(0,0,0);
        if (floor.getLightLevel() == 0) {
            return true;
        }
        return false;
    } 

    public boolean hasTorches(Player player) {
        return player.getInventory().contains(Material.TORCH);
    }

    public boolean isPlaceableFloor(Player player) {
        Block floor = player.getLocation().getBlock().getRelative(0,-1,0);

        if (floor.getType().isSolid()) {
            if (floor.getRelative(0,1,0).getType().equals(Material.AIR)) {
                return true;
            }
        }
        
        return false;
    }

    public void illuminate(Player player) {
        if (player.getInventory().first(Material.TORCH) != -1) {
            ItemStack torches = player.getInventory().getItem(player.getInventory().first(Material.TORCH));
            torches.setAmount(torches.getAmount()-1);
            player.getLocation().getBlock().setType(Material.TORCH);
        }
    }
}
