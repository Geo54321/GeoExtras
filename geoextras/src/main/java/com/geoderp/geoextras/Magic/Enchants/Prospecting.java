package com.geoderp.geoextras.Magic.Enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.geoderp.geoextras.Magic.MagicList;

public class Prospecting implements Listener {
    private List<Material> validOres = new ArrayList<Material>();
    private int maxVein;

    public Prospecting(int maxVein, boolean doStoneTypes) {
        this.maxVein = maxVein;

        if (doStoneTypes) {
            validOres.add(Material.ANDESITE);
            validOres.add(Material.DIORITE);
            validOres.add(Material.GRANITE);
            validOres.add(Material.TUFF);
            validOres.add(Material.MAGMA_BLOCK);
            validOres.add(Material.DRIPSTONE_BLOCK);
        }

        // Add base ore types here
        validOres.add(Material.ANCIENT_DEBRIS);
        validOres.add(Material.AMETHYST_BLOCK);
        validOres.add(Material.RAW_IRON_BLOCK);
        validOres.add(Material.RAW_COPPER_BLOCK);
        validOres.add(Material.COAL_BLOCK);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.prospecting")) {
            // Has perms
            ItemStack tool = player.getInventory().getItemInMainHand();
            if (isEnchantedItem(tool)) {
                // Using prospecting item
                if (isProspect(event.getBlock().getType())) {
                    // Is valid ore
                    doProspect(event.getBlock(), tool);
                }
            }
        }
    }

    public boolean isEnchantedItem(ItemStack item) {
        return MagicList.getMagicByString("prospecting").isMagicItem(item);
    }

    public boolean isProspect(Material origin) {
        if (origin.toString().contains("ORE")) {
            return true;
        }
        if (validOres.contains(origin)) {
            return true;
        }
        return false;
    }

    public void doProspect(Block origin, ItemStack tool) {
        List<Block> vein = getEveryOreBlock(origin);

        if (vein != null) {
            for (Block ore : vein) {
                ore.breakNaturally(tool);
            }
            MagicList.getMagicByString("prospecting").damageItem(tool, vein.size());
        }
    }

    public ArrayList<Block> getEveryOreBlock(Block origin) {
        ArrayList<Block> oreBlocks = new ArrayList<Block>();
        oreBlocks.add(origin);

        for (int g = 0; g < oreBlocks.size(); g++) {
            ArrayList<Block> temp = getNearbyBlocks(oreBlocks.get(g));
            for (Block block : temp) {
                if (!oreBlocks.contains(block)) {
                    oreBlocks.add(block);
                }
            }
            if (oreBlocks.size() > maxVein) {
                return oreBlocks;
            }
        }

        return oreBlocks;
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
