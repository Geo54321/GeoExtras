package com.geoderp.geoextras.Magic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConflictChecker implements Listener {
    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        ItemStack left = event.getInventory().getItem(0);
        ItemStack right = event.getInventory().getItem(1);
        ItemStack result = event.getInventory().getItem(2);

        if (left != null && right != null && result != null) {
            // All anvil items exist

            List<Magic> allCustomEnchants = new ArrayList<Magic>();
            
            if (isEnchanted(left) != null) {
                allCustomEnchants.addAll(isEnchanted(left));
            }
            if (isEnchanted(right) != null) {
                allCustomEnchants.addAll(isEnchanted(right));
            }
            if (allCustomEnchants.size() > 0) {
                // At least one custom enchant
                event.setResult(alterResult(allCustomEnchants, result));
            }
        }
    }

    public List<Magic> isEnchanted(ItemStack item) {
        List<Magic> enchants = new ArrayList<Magic>();

        for (Magic magic : MagicList.magic) {
            if (magic.isMagicItem(item)) {
                enchants.add(magic);
            }
        }

        if (enchants.size() == 0)
            return null;    
        else 
            return enchants;
    }

    public ItemStack alterResult(List<Magic> custom, ItemStack result) {
        // Custom enchant collisions
        if (custom.size() > 1) {
            if (custom.contains(MagicList.getMagicByString("prospecting")) && custom.contains(MagicList.getMagicByString("quarrying"))) {
                custom.remove(MagicList.getMagicByString("quarrying"));
            }
        }
        
        // Custom to normal enchant collisions
        List<Magic> notValid = new ArrayList<Magic>();
        for (Magic magic : custom) {
            if (magic.getConflicts() != null) {
                // custom enchant has conflicts
                Enchantment[] conflicts = magic.getConflicts();
                for (Enchantment enchant : conflicts) {
                    if (result.getEnchantments().containsKey(enchant)) {
                        // Enchant does not conflict 
                        notValid.add(magic);
                    }
                }
            }
        }
        custom.removeAll(notValid);

        // Generate combined lore
        List<String> lore = new ArrayList<String>();
        for (Magic magic : custom) {
            lore.add(magic.getLore());
        }

        // Create result
        ItemMeta meta = result.getItemMeta();
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
