package com.geoderp.geoextras.Magic.Enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.geoderp.geoextras.Magic.MagicList;

public class Illumination implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.illumination")) {
            // has perms
            if (isEnchantedItem(player.getInventory().getItemInOffHand())) {
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

    public boolean isEnchantedItem(ItemStack item) {
        return MagicList.getMagicByString("illumination").isMagicItem(item);
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
            Block footspace = floor.getRelative(0, 1, 0);
            if (footspace.getType().equals(Material.AIR) || footspace.getType().equals(Material.CAVE_AIR) || footspace.getType().equals(Material.VOID_AIR)) {
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
