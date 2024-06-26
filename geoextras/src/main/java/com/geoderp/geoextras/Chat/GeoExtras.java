package com.geoderp.geoextras.Chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class GeoExtras implements CommandExecutor {
    JavaPlugin plugin;

    public GeoExtras(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("GeoExtras.commands.geoextras")) {
            if (args[0].equals("reload")) {
                if (sender.hasPermission("GeoExtras.commands.geoextras.reload")) {
                    reloadConfig(sender);
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