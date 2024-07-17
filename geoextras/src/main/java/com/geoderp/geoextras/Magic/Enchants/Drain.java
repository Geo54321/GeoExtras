package com.geoderp.geoextras.Magic.Enchants;

import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.geoderp.geoextras.Magic.MagicList;

public class Drain implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            // Is player damaging
            Player player = (Player) event.getDamager();
            if (player.hasPermission("GeoExtras.magic.enchants.drain")) {
                // Has permissions
                if (isEnchantedItem(player.getInventory().getItemInMainHand())) {
                    // Is holding a drain item
                    if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() != player.getHealth()) {
                        // Less that full health
                        doDrain(player);
                    }
                }
            }
        }
    }

    public boolean isEnchantedItem(ItemStack item) {
        return MagicList.getMagicByString("drain").isMagicItem(item);
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
