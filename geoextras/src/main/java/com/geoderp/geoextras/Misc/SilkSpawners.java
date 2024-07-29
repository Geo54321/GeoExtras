package com.geoderp.geoextras.Misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SilkSpawners implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (isSpawner(block)) {
            // Block is a spawner
            if (player.hasPermission("GeoExtras.misc.silk.nosilk")) {
                // Player has silk perm without silk touch required
                dropSpawner(block);
            }
            else if (player.hasPermission("GeoExtras.misc.silk.silk")) {
                // Player has silk perm
                if (isSilkTouch(event.getPlayer().getInventory().getItemInMainHand())) {
                    // Item is silk touch
                    dropSpawner(block);
                }
            }
            else {
                // no perms :)
            }
        }
    }

    public boolean isSpawner(Block block) {
        return block.getType().equals(Material.SPAWNER);
    }

    public boolean isSilkTouch(ItemStack tool) {
        return tool.containsEnchantment(Enchantment.SILK_TOUCH);
    }

    public void dropSpawner(Block block) {
        CreatureSpawner origin = (CreatureSpawner) block.getState();
        if (origin.getSpawnedType() == null) {
            return;
        }

        // Create a new spawner and give it custom lore to determine spawner type when placed
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        ItemMeta meta = spawner.getItemMeta();
        List<String> lore = new ArrayList<String>();
        String loreStr = "§2" + origin.getSpawnedType().toString();
        lore.add(loreStr);
        meta.setLore(lore);
        String display = "§fMonster Spawner (" + loreStr + "§f)";
        meta.setDisplayName(display);
        spawner.setItemMeta(meta);

        block.getWorld().dropItemNaturally(block.getLocation(), spawner);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item != null && item.getItemMeta() != null) {
            // item exists and has meta
            ItemMeta meta = item.getItemMeta();
            if (item.getType().equals(Material.SPAWNER) && meta.hasLore()) {
                // item is a spawner and it has lore
                for (String str : meta.getLore()) {
                    if (str.contains("§2")) {
                        // found spawner type
                        str = str.substring(2);

                        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
                        spawner.setSpawnedType(EntityType.valueOf(str));
                        spawner.update();
                    }
                }
            }
        }
    }
}
