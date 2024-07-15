package com.geoderp.geoextras.Magic;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerTempventory {
    private Player player;
    private List<ItemStack> items;

    public PlayerTempventory(Player player, List<ItemStack> items) {
        this.player = player;
        this.items = items;
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public void setItems(List<ItemStack> newItems) {
        this.items = newItems;
    }
}
