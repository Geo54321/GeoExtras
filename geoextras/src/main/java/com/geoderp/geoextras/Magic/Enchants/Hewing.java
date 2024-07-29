package com.geoderp.geoextras.Magic.Enchants;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.inventory.ItemStack;

import com.geoderp.geoextras.Magic.MagicList;

public class Hewing implements Listener {
    private int maxBlocks;

    public Hewing(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    @EventHandler
    public void onPlayerInteract(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.hewing")) {
            // Has perms
            ItemStack tool = player.getInventory().getItemInMainHand();
            if (isEnchantedItem(tool)) {
                // Block was broken with hewing item
                if (isTree(event.getBlock())) {
                    // Block was part of a tree
                    doHew(event.getBlock(), tool);
                }
            }
        }
    }

    public boolean isEnchantedItem(ItemStack item) {
        return MagicList.getMagicByString("hewing").isMagicItem(item);
    }

    public boolean isWood(Material mat) {
        return mat.toString().contains("LOG") || mat.toString().contains("STEM") || mat.toString().contains("ROOTS");
    }

    public boolean isLeaf(Material mat) {
        return mat.toString().contains("LEAVES") || mat.toString().contains("WART");
    }

    public boolean isWeirdTree(Block lastLog) {
        // acacia trees can spawn with no leaves above the trunk, this checks for that
        for (int y = 0; y < 8; y++) {
            for (int x = -2; x < 3; x++) {
                for (int z = -2; z < 3; z++) {
                    if (lastLog.getRelative(x,y,z).getType().toString().contains("LEAVES")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isTree(Block origin) {
        Material original = origin.getType();
        int height = 0;
        if (isWood(original)) {
            while (origin.getRelative(0,height,0).getType().equals(original)) {
                height++;
                if (height > maxBlocks) {
                    return false;
                }
                if (isLeaf(origin.getRelative(0,height,0).getType())) {
                    return true;
                }
            }
            
            return isWeirdTree(origin.getRelative(0,height,0));
        }
        
        return false;
    }

    public void doHew(Block origin, ItemStack tool) {
        ArrayList<Block> tree = getEveryTreeBlock(origin);

        if (tree == null) {
            // Tree was too big
            return;
        }
        for (Block log : tree) {
            log.breakNaturally();
        }
        MagicList.getMagicByString("hewing").damageItem(tool, tree.size());
    }

    public ArrayList<Block> getEveryTreeBlock(Block origin) {
        ArrayList<Block> treeBlocks = new ArrayList<Block>();
        treeBlocks.add(origin);

        for (int g = 0; g < treeBlocks.size(); g++) {
            ArrayList<Block> temp = getNearbyBlocks(treeBlocks.get(g));
            for (Block block : temp) {
                if (!treeBlocks.contains(block)) {
                    treeBlocks.add(block);
                }
            }
            if (treeBlocks.size() > this.maxBlocks) {
                return null;
            }
        }

        return treeBlocks;
    }

    public ArrayList<Block> getNearbyBlocks(Block origin) {
        ArrayList<Block> validBlocks = new ArrayList<Block>();
        Material originalType = origin.getType();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (origin.getRelative(x, y, z).getType().equals(originalType)) {
                        validBlocks.add(origin.getRelative(x, y, z));
                    }
                }
            }
        }

        return validBlocks;
    }
}
