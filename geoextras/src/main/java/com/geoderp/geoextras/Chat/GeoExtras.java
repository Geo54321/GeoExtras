package com.geoderp.geoextras.Chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.geoderp.geoextras.Disconnect.DisconnectFileWorker;

public class GeoExtras implements CommandExecutor {
    JavaPlugin plugin;
    DisconnectFileWorker fileWorker;

    public GeoExtras(JavaPlugin plugin, DisconnectFileWorker fileWorker) {
        this.plugin = plugin;
        this.fileWorker = fileWorker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("GeoExtras.commands.geoextras")) {
            if (args[0].equals("reload")) {
                if (sender.hasPermission("GeoExtras.commands.geoextras.reload")) {
                    reloadConfig(sender);
                    fileWorker.reloadMessages();
                }
                else {
                    sender.sendMessage("§cSorry but you do not have permission to reload the GeoExtras config.");
                }
            }
        }
        return false;
    }

    public void reloadConfig(CommandSender sender) {
        plugin.reloadConfig();
        sender.sendMessage("§2Config file reloaded!");
    }
}