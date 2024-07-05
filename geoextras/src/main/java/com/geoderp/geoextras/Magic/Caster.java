package com.geoderp.geoextras.Magic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

public class Caster implements CommandExecutor,TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("GeoExtras.magic.command")) {
            if (args.length == 2) {
                // Spawn on other
                if (validMagic(args[0]) && validPlayer(args[1])) {
                    spawnMagicedItem(Bukkit.getPlayer(args[1]), MagicList.getMagicByString(args[0]));
                }
            }
            else if (args.length == 1) {
                if (sender instanceof Player) {
                    // Spawn on self
                    if (validMagic(args[0])) {
                        spawnMagicedItem((Player) sender, MagicList.getMagicByString(args[0]));
                    }
                }
                else {
                    sender.sendMessage("§cConsole cannot hold items.");
                }
            }
            else if (args.length == 0) {
                // No args -- display valid enchants
                sender.sendMessage("§4Valid Enchants: §b" + MagicList.magicStrings);
            }
            else {
                sender.sendMessage("§cSorry but that is not a valid number of arguments. Please try again.");
            }
        }
        else {
            sender.sendMessage("§cSorry but you do not have permission to use the /geomagic command.");
        }

        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<String>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], MagicList.magicStrings, completions);
            return completions;
        }
        else {
            return null;
        }
    }

    public boolean validMagic(String arg) {
        for (String magic : MagicList.magicStrings) {
            if (magic.equals(arg)) {
                return true;
            }
        }
        return false;
    }

    public boolean validPlayer(String arg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(arg)) {
                return true;
            }
        }
        return false;
    }

    public void spawnMagicedItem(Player player, Magic magic) {
        ItemStack item = magic.getMagicedItem();

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
        else {
            player.getInventory().addItem(item);
        }
    }
}
