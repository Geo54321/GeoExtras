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
    private List<Material> validBlocks = new ArrayList<Material>();

    public Quarrying() {
        validBlocks.add(Material.ANCIENT_DEBRIS);
        validBlocks.add(Material.AMETHYST_BLOCK);
        validBlocks.add(Material.COAL_BLOCK);
        validBlocks.add(Material.STONE);
        validBlocks.add(Material.COBBLESTONE);
        validBlocks.add(Material.NETHERRACK);
        validBlocks.add(Material.NETHER_BRICKS);
        validBlocks.add(Material.DEEPSLATE);
        validBlocks.add(Material.TUFF);
        validBlocks.add(Material.DIORITE);
        validBlocks.add(Material.ANDESITE);
        validBlocks.add(Material.GRANITE);
        validBlocks.add(Material.TUFF);
        validBlocks.add(Material.SANDSTONE);
        validBlocks.add(Material.RED_NETHER_BRICKS);
        validBlocks.add(Material.BLACKSTONE);
        validBlocks.add(Material.BASALT);
        validBlocks.add(Material.END_STONE);
        validBlocks.add(Material.ANCIENT_DEBRIS);
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

    public boolean validQuarry(Material origin) {
        if (origin.toString().contains("ORE") || origin.toString().contains("RAW") || origin.toString().contains("PRISMARINE") || origin.toString().contains("TERRACOTTA")) {
            return true;
        }
        if (this.validBlocks.contains(origin)) {
            return true;
        }
        return false;
    }

    public void doQuarry(Block origin, BlockFace dir, ItemStack tool) {
        int count = 0;
        switch (dir) {
            case UP:
            case DOWN:
                // No y change
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        Block curr = origin.getRelative(x,0,z);
                        if (validQuarry(curr.getType())) {
                            curr.breakNaturally(tool);
                            count++;
                        }
                    }
                }
            break;
            case NORTH:
            case SOUTH:
                // No z change
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        Block curr = origin.getRelative(x,y,0);
                        if (validQuarry(curr.getType())) {
                            curr.breakNaturally(tool);
                            count++;
                        }
                    }
                }
            break;
            case EAST:
            case WEST:
                // No x change
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Block curr = origin.getRelative(0,y,z);
                        if (validQuarry(curr.getType())) {
                            curr.breakNaturally(tool);
                            count++;
                        }
                    }
                }
            break;
            default:
                // Something weird has occured
            break;
        }
        MagicList.getMagicByString("quarrying").damageItem(tool, count);
    }
}
