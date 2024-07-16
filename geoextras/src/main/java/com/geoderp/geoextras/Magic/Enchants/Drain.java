package com.geoderp.geoextras.Magic.Enchants;

import java.util.List;

import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.geoderp.geoextras.Magic.MagicList;

public class Drain implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            // Is player damaging
            Player player = (Player) event.getDamager();
            if (player.hasPermission("GeoExtras.magic.enchants.drain")) {
                // Has permissions
                if (isDrainItem(player.getInventory().getItemInMainHand())) {
                    // Is holding a drain item
                    if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() != player.getHealth()) {
                        // Less that full health
                        doDrain(player);
                    }
                }
            }
        }
    }

    public boolean isDrainItem(ItemStack item) {
        if (item != null) {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    for (String line : lore) {
                        if (line.equals(MagicList.getMagicByString("drain").getLore())) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public void doDrain(Player player) {
        if (player.getHealth() >= player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 1) {
            // Near max health - set max health
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
        else {
            // Not near max health - increment
            player.setHealth(player.getHealth()+1);
        }

        player.getWorld().spawnParticle(Particle.HEART, player.getLocation(), (int) (5 + Math.random() * 5),0.5,1,0.5);
    }
}
