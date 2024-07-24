package com.geoderp.geoextras.Magic.Enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.geoderp.geoextras.Magic.MagicList;

public class Quarrying implements Listener {
    private List<Material> validPickableBlocks = new ArrayList<Material>();
    private List<Material> validDiggableBlocks = new ArrayList<Material>();

    public Quarrying() {
        validPickableBlocks.add(Material.ANCIENT_DEBRIS);
        validPickableBlocks.add(Material.AMETHYST_BLOCK);
        validPickableBlocks.add(Material.COAL_BLOCK);
        validPickableBlocks.add(Material.STONE);
        validPickableBlocks.add(Material.COBBLESTONE);
        validPickableBlocks.add(Material.NETHERRACK);
        validPickableBlocks.add(Material.NETHER_BRICKS);
        validPickableBlocks.add(Material.DEEPSLATE);
        validPickableBlocks.add(Material.COBBLED_DEEPSLATE);
        validPickableBlocks.add(Material.DIORITE);
        validPickableBlocks.add(Material.ANDESITE);
        validPickableBlocks.add(Material.GRANITE);
        validPickableBlocks.add(Material.TUFF);
        validPickableBlocks.add(Material.SANDSTONE);
        validPickableBlocks.add(Material.RED_NETHER_BRICKS);
        validPickableBlocks.add(Material.BLACKSTONE);
        validPickableBlocks.add(Material.BASALT);
        validPickableBlocks.add(Material.END_STONE);
        validPickableBlocks.add(Material.ANCIENT_DEBRIS);
        validPickableBlocks.add(Material.SMOOTH_BASALT);
        validPickableBlocks.add(Material.CALCITE);

        validDiggableBlocks.add(Material.DIRT);
        validDiggableBlocks.add(Material.GRASS_BLOCK);
        validDiggableBlocks.add(Material.SAND);
        validDiggableBlocks.add(Material.RED_SAND);
        validDiggableBlocks.add(Material.GRAVEL);
        validDiggableBlocks.add(Material.COARSE_DIRT);
        validDiggableBlocks.add(Material.MYCELIUM);
        validDiggableBlocks.add(Material.PODZOL);
        validDiggableBlocks.add(Material.MUD);
        validDiggableBlocks.add(Material.SOUL_SAND);
        validDiggableBlocks.add(Material.SOUL_SOIL);
        validDiggableBlocks.add(Material.SNOW_BLOCK);
        validDiggableBlocks.add(Material.CLAY);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.quarrying")) {
            // Has perms
            ItemStack tool = player.getInventory().getItemInMainHand();
            if (isEnchantedItem(tool)) {
                // Using quarrying item
                List<Block> lastBlocks = player.getLastTwoTargetBlocks(null, 5);
                BlockFace face = lastBlocks.get(1).getFace(lastBlocks.get(0));
                doQuarry(event.getBlock(), face, tool);
            }
        }
    }

    public boolean isEnchantedItem(ItemStack item) {
        return MagicList.getMagicByString("quarrying").isMagicItem(item);
    }

    public boolean isForgeItem(ItemStack item) {
        return MagicList.getMagicByString("forge").isMagicItem(item);
    }

    public boolean isShovel(ItemStack item) {
        return item.getType().toString().contains("SHOVEL");
    }

    public boolean validQuarry(Material origin, ItemStack tool) {
        if (isShovel(tool)) {
            return this.validDiggableBlocks.contains(origin);
        }
        else {
            if (origin.toString().contains("ORE") || origin.toString().contains("RAW") || origin.toString().contains("PRISMARINE") || origin.toString().contains("TERRACOTTA")) {
                return true;
            }
            if (this.validPickableBlocks.contains(origin)) {
                return true;
            }
        }
        return false;
    }

    public void doQuarry(Block origin, BlockFace dir, ItemStack tool) {
        List<Block> validTargets = new ArrayList<Block>();
        List<Block> allTargetBlocks = getTargetBlocks(origin, dir);
        
        for (Block block : allTargetBlocks) {
            if (validQuarry(block.getType(), tool)) {
                validTargets.add(block);
            }
        }
        
        if (isForgeItem(tool)) {
            for (Block block : validTargets) {
                if (Forge.isForgableBlock(block.getType())) {
                    Forge.doForge(block, tool);
                    block.setType(Material.AIR);
                }
                else {
                    block.breakNaturally(tool);
                }
            }
        }
        else {
            for (Block block : validTargets) {
                block.breakNaturally(tool);
            }
        }
        
        MagicList.getMagicByString("quarrying").damageItem(tool, validTargets.size());
    }

    public List<Block> getTargetBlocks(Block origin, BlockFace dir) {
        List<Block> targets = new ArrayList<Block>();
        switch (dir) {
            case UP:
            case DOWN:
                // No y change
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        targets.add(origin.getRelative(x,0,z));
                    }
                }
            break;
            case NORTH:
            case SOUTH:
                // No z change
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        targets.add(origin.getRelative(x,y,0));
                    }
                }
            break;
            case EAST:
            case WEST:
                // No x change
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        targets.add(origin.getRelative(0,y,z));
                    }
                }
            break;
            default:
                // Something weird has occured
            break;
        }

        return targets;
    }
}
