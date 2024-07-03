package com.geoderp.geoextras.Farm;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Scythe implements Listener {
    JavaPlugin plugin;

    public Scythe(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("GeoExtras.farm.scythe")) {
            // Has permission
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                // Right clicked
                if (event.getClickedBlock() != null) {
                    // Has a target block
                    if (event.getClickedBlock().getType().equals(Material.SHORT_GRASS) || event.getClickedBlock().getType().equals(Material.TALL_GRASS) || event.getClickedBlock().getType().equals(Material.FERN) || event.getClickedBlock().getType().equals(Material.LARGE_FERN)) {
                        // Target is a type of grass
                        if (player.getInventory().getItemInMainHand().getType().equals(Material.SHEARS) || player.getInventory().getItemInOffHand().getType().equals(Material.SHEARS)) {
                            // Is holding shears
                            killGrass(event.getClickedBlock().getLocation());
                        }
                    }
                }
            }
        }
    }

    public void killGrass(Location origin) {
        int range = plugin.getConfig().getInt("options.scythe-range");
        for(int x = (range * -1); x <= range; x++) {
            for(int y = (range * -1); y <= range; y++) {
                for(int z = (range * -1); z <= range; z++) {
                    //Location target = new Location(origin.getWorld(), origin.getX()+x, origin.getY()+y, origin.getZ()+z);
                    Block target = origin.getWorld().getBlockAt(origin.getBlockX()+x, origin.getBlockY()+y, origin.getBlockZ()+z);
                    if(target.getType().equals(Material.TALL_GRASS) || target.getType().equals(Material.LARGE_FERN)) {
                        removeTarget(target);
                        origin.getWorld().getBlockAt(origin.getBlockX(), origin.getBlockY()+1, origin.getBlockZ()).setType(Material.AIR);
                    } else if(target.getType().equals(Material.SHORT_GRASS) || target.getType().equals(Material.FERN)) {
                        removeTarget(target);
                    }
                }
            }
        }
    }

    public void removeTarget(Block target) {
        Iterator<ItemStack> drops = target.getDrops().iterator();
        while (drops.hasNext()) {
            target.getWorld().dropItemNaturally(target.getLocation(), drops.next());
        }
        target.setType(Material.AIR);
        target.getWorld().playSound(target.getLocation(), Sound.BLOCK_GRASS_BREAK, (float) 0.25, (float) (0.96 + Math.random() * 0.24));
        target.getWorld().spawnParticle(Particle.COMPOSTER, target.getLocation(), (int) (1 + Math.random() * 2),0,1,0);
    }
}
