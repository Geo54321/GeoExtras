package com.geoderp.geoextras.Disconnect;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DisconnectMessage implements Listener {
    JavaPlugin plugin;
    DisconnectFileWorker fileWorker;
    ArrayList<String> disconnectMessages = new ArrayList<String>();

    public DisconnectMessage(boolean silly, JavaPlugin plugin, DisconnectFileWorker fileWorker) {
        this.plugin = plugin;
        this.fileWorker = fileWorker;
        generateMessages(silly);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        Bukkit.broadcastMessage(getRandomDisconnectMessage(player));
    }

    public void generateMessages(boolean silly) {
        if (silly) {
            disconnectMessages = fileWorker.getMessages();
        } else {
            disconnectMessages.add("# left the server.");
        }
    }

    public String getRandomDisconnectMessage(Player player) {
        String fullMsg = "§7[§2.o/§7] "; //prefix
        fullMsg += "§c"; // red
        //fullMsg += "§e"; // yellow
        String msg = disconnectMessages.get(0);

        if (disconnectMessages.size() > 1) {
            Random rng = new Random();
            msg = disconnectMessages.get(rng.nextInt(disconnectMessages.size()));
        } 
        
        msg.replace("#",player.getName());
        fullMsg += msg;

        return fullMsg;
    }
}
