package com.geoderp.geoextras.Magic.Enchants;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.geoderp.geoextras.Magic.MagicList;

public class Paring implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.paring")) {
            // Has perms
            ItemStack tool = player.getInventory().getItemInMainHand();
            if (isEnchantedItem(tool)) {
                // Using paring item
                if (isLeaves(event.getBlock())) {
                    // Is leaves
                    doPare(event.getBlock(), tool);
                }
            }
        }
    }

    public boolean isEnchantedItem(ItemStack item) {
        return MagicList.getMagicByString("paring").isMagicItem(item);
    }

    public boolean isLeaves(Block block) {
        if (block.getType().toString().contains("LEAVES") || block.getType().toString().contains("WART")) {
            return true;
        }
        return false;
    }

    public void doPare(Block origin, ItemStack tool) {
        int count = 0;
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    Block curr = origin.getRelative(x,y,z);
                    if (curr.getType().equals(origin.getType())) {
                        curr.breakNaturally(tool);
                        count++;
                    }
                }
            }
        }

        MagicList.getMagicByString("paring").damageItem(tool, count);
    }
}
