package com.geoderp.geoextras.Silly;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class LapisElevators implements Listener {
    private int maxRange;

    public LapisElevators(int maxRange) {
        this.maxRange = maxRange;
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.misc.elevators")) {
            Location pos = player.getLocation();
            Block floor = pos.add(0,-1,0).getBlock();
            if(floor.getType().equals(Material.LAPIS_BLOCK)) {
                if (event.isSneaking()) {
                    Block validElevator = checkElevator(floor, false);
                    if (validElevator != null) {
                        doElevator(player, validElevator);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.misc.elevators")) {
            Location pos = player.getLocation();
            Block floor = pos.add(0,-1,0).getBlock();
            if(floor.getType().equals(Material.LAPIS_BLOCK)) {
                if (event.getTo().getBlockY() >= event.getFrom().getBlockY()+1) {
                    Block validElevator = checkElevator(floor, true);
                    if (validElevator != null) {
                        doElevator(player, validElevator);
                    }
                }
            }
        }
    }

    public Block checkElevator(Block orig, boolean up) {
        Block target = orig;
        for(int g = 0; g < this.maxRange; g++) {
            if (up) {
                // Upavator
                target = target.getRelative(BlockFace.UP);
            }
            else {
                // Downavator
                target = target.getRelative(BlockFace.DOWN);
            }
            
            if (target.getType().equals(Material.LAPIS_BLOCK)) {
                // Is valid elevator block
                if (target.getRelative(BlockFace.UP).getType().equals(Material.AIR) && target.getRelative(BlockFace.UP,2).getType().equals(Material.AIR)) {
                    // Has air gap above elevator - valid elevator
                    return target;
                }
            }
        }
        return null;
    }

    public void doElevator(Player player, Block dest) {
        Location loc = player.getLocation();
        loc.setX(dest.getX()+0.5);
        loc.setY(dest.getY()+1);
        loc.setZ(dest.getZ()+0.5);
        dest.getWorld().spawnParticle(Particle.ENCHANT, loc.getBlockX()+0.5,loc.getBlockY(),loc.getBlockZ()+0.5, 25,Math.random()*0.25,Math.random()*0.10,Math.random()*0.25,0);
        dest.getWorld().playSound(loc, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, (float) 0.85, (float) 0.96);
        player.teleport(loc);
    }
}
