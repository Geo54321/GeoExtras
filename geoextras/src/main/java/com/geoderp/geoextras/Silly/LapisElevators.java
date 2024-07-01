package com.geoderp.geoextras.Silly;

import org.bukkit.Location;
import org.bukkit.Material;
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
    public void onPlayerSnake(PlayerToggleSneakEvent event) {
        if (event.getPlayer().hasPermission("GeoExtras.silly.elevators")) {
            Location floor = event.getPlayer().getLocation().add(0,-1,0);
            if (floor.getBlock().getType().equals(Material.LAPIS_BLOCK)) {
                if (event.getPlayer().isSneaking()) {
                    // Sneaking - attempt down elevator
                    doElevator(floor, -1, event.getPlayer());
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().hasPermission("GeoExtras.silly.elevators")) {
            Location floor = event.getFrom().add(0,-1,0);
            if (floor.getBlock().getType().equals(Material.LAPIS_BLOCK)) {
                if (event.getTo().getY() > event.getFrom().getY() + 1) {
                    // Jumping - attempt up elevator
                    doElevator(floor, 1, event.getPlayer());
                }
            }
        }
    }
    
    public double checkElevator(Location from, int direction, Player player) {
        Location to = from;

        for (int g = 1; g < maxRange; g++) {
            if (direction > 0) {
                // Going up
                to.setY(to.getY()+1);
            }
            else {
                // Going down
                to.setY(to.getY()-1);
            }
            
            if (to.getBlock().getType().equals(Material.LAPIS_BLOCK)) {
                // Found a potnential elevator
                Location air1 = to.add(0,1,0);
                Location air2 = to.add(0,2,0);
                if (air1.getBlock().getType().equals(Material.AIR) && air2.getBlock().getType().equals(Material.AIR)) {
                    // airspace is clear - valid elevator
                    return to.getY() - 1.5;
                }
            }
        }

        return 0;
    }

    public void doElevator(Location floor, int direction, Player player) {
        double elevator = checkElevator(floor, direction, player);

        if (elevator != 0) {
            floor.setY(elevator);
            //particle effects on both elevators
            player.teleport(floor);
            //player.playSound();
        }
    }
}
