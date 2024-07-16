package com.geoderp.geoextras.Magic.Enchants;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.geoderp.geoextras.Magic.MagicList;

public class Paring implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.paring")) {
            // Has perms
            ItemStack tool = player.getInventory().getItemInMainHand();
            if (isParingItem(tool)) {
                // Using paring item
                if (isLeaves(event.getBlock())) {
                    // Is leaves
                    doPare(event.getBlock(), tool);
                }
            }
        }
    }

    public boolean isParingItem(ItemStack item) {
        if (item != null) {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    for (String line : lore) {
                        if (line.equals(MagicList.getMagicByString("paring").getLore())) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public boolean isLeaves(Block block) {
        if (block.getType().toString().contains("LEAVES") || block.getType().toString().contains("WART")) {
            return true;
        }
        return false;
    }

    public void doPare(Block origin, ItemStack tool) {
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    Block curr = origin.getRelative(x,y,z);
                    if (curr.getType().equals(origin.getType())) {
                        curr.breakNaturally(tool);
                    }
                }
            }
        }
    }
}
