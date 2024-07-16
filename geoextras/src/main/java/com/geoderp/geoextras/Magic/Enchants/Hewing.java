package com.geoderp.geoextras.Magic.Enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.geoderp.geoextras.Magic.MagicList;

public class Hewing implements Listener {
    private int maxBlocks;

    public Hewing(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    @EventHandler
    public void onPlayerInteract(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("GeoExtas.magic.enchants.*")) {
            // Has perms
            if (isHewingItem(event.getPlayer().getInventory().getItemInMainHand())) {
                // Block was broken with hewing item
                if (isTree(event.getBlock())) {
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
                        if (line.equals(MagicList.getMagicByString("hewing").getLore())) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public boolean isWood(Material mat) {
        return mat.toString().contains("LOG") || mat.toString().contains("STEM");
    }

    public boolean isLeaf(Material mat) {
        return mat.toString().contains("LEAVES") || mat.toString().contains("WART");
    }

    public boolean acaciaSucks(Block lastLog) {
        // acacia trees can spawn with no leaves above the trunk, this checks for that
        for (int y = 0; y < 4; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    if (lastLog.getRelative(x,y,z).getType().equals(Material.ACACIA_LEAVES)) {
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
        while (origin.getRelative(0,height,0).getType().equals(original)) {
            height++;
            if (height > maxBlocks) {
                return false;
            }
            if (isLeaf(origin.getRelative(0,height,0).getType())) {
                return true;
            }
        }

        return acaciaSucks(origin.getRelative(0,height,0));
    }

    public void doHew(Block origin) {
        ArrayList<Block> tree = getEveryTreeBlock(origin);

        if (tree == null) {
            // Tree was too big
            return;
        }
        for (Block log : tree) {
            log.breakNaturally();
        }
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
            if (treeBlocks.size() > maxBlocks) {
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
