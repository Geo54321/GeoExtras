package com.geoderp.geoextras.Magic.Enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.geoderp.geoextras.Magic.MagicList;
import com.geoderp.geoextras.Magic.PlayerTempventory;

public class Deathwoven implements Listener {
    private List<PlayerTempventory> tempInventories = new ArrayList<PlayerTempventory>();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasPermission("GeoExtras.magic.enchants.deathwoven")) {
            // Has perms
            List<ItemStack> normalDrops = event.getDrops();
            List<ItemStack> saveDrops = new ArrayList<ItemStack>();

            for (ItemStack item : normalDrops) {
                if (isEnchantedItem(item)) {
                    // Is deathwoven item
                    saveDrops.add(item);
                }
            }
            saveItems(player, saveDrops);
            
            if (saveDrops.size() > 0) {
                event.getDrops().removeAll(saveDrops);
                player.sendMessage("§c§k!mmm!§r§c WARNING §k!mmm!§r§b --- If you go to the title screen right now your deathwoven items will be deleted. ---  §c§k!mmm!§r§c WARNING §k!mmm!§r");
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("GeoExtras.magic.enchants.deathwoven")) {
            // Has perms
            PlayerTempventory temp = getTempventory(player);
            if (temp != null) {
                for (ItemStack item : temp.getItems()) {
                    player.getInventory().addItem(item);
                }
            }
        }
    }

    public boolean isEnchantedItem(ItemStack item) {
        return MagicList.getMagicByString("deathwoven").isMagicItem(item);
    }

    public PlayerTempventory getTempventory(Player player) {
        for(PlayerTempventory temp : tempInventories) {
            if (temp.getPlayer().equals(player)) {
                tempInventories.remove(temp);
                return temp;
            }
        }
        return null;
    }

    public void saveItems(Player player, List<ItemStack> items) {
        PlayerTempventory temp = getTempventory(player);
        if (temp == null) {
            temp = new PlayerTempventory(player, items);
        }
        else {
            temp.setItems(items);
        }

        tempInventories.add(temp);
    }
}
