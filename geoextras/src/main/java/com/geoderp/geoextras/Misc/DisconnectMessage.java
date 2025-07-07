package com.geoderp.geoextras.Misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectMessage implements Listener {
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage(getRandomDisconnectMessage(player));
    }

    public String getRandomDisconnectMessage(Player player) {
        String msg = "§c";

        msg += player.getName() + " has left the server.";

        return msg;
    }
}
