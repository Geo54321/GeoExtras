package com.geoderp.geoextras.Magic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.geoderp.geoextras.Magic.Magic.BaseType;

public class ConflictChecker implements Listener {
    ItemStack anvilResult = null;

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        this.anvilResult = null;
        ItemStack left = event.getInventory().getItem(0);
        ItemStack right = event.getInventory().getItem(1);
        ItemStack result = event.getInventory().getItem(2);

        if (left != null && right != null) {
            if (result != null || (left.getType().equals(Material.BOOK) || right.getType().equals(Material.BOOK))) {
                // All necessary anvil items exist
                List<Magic> allCustomEnchants = new ArrayList<Magic>();
                
                if (isEnchanted(left) != null) {
                    allCustomEnchants.addAll(isEnchanted(left));
                }
                if (isEnchanted(right) != null) {
                    allCustomEnchants.addAll(isEnchanted(right));
                }

                allCustomEnchants = hasValidBase(allCustomEnchants, result, left);
                allCustomEnchants = removeDuplicates(allCustomEnchants);

                if (allCustomEnchants.size() > 0) {
                    // At least one custom enchant
                    this.anvilResult = alterResult(allCustomEnchants, result, left);
                    event.setResult(this.anvilResult);

                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv != null) {
            if (inv.getType().equals(InventoryType.ANVIL) && event.getSlotType().equals(SlotType.RESULT) && this.anvilResult != null) {
                event.setCursor(this.anvilResult);
                
                ItemStack temp = inv.getItem(0);
                if (temp.getAmount() > 1) {
                    temp.setAmount(temp.getAmount()-1);
                    inv.setItem(0,temp);
                }
                else {
                    inv.setItem(0, null);
                }

                temp = inv.getItem(1);
                if (temp.getAmount() > 1) {
                    temp.setAmount(temp.getAmount()-1);
                    inv.setItem(1,temp);
                }
                else {
                    inv.setItem(1, null);
                }
                
                inv.setItem(2, null);
                this.anvilResult = null;
            }
        }
    }

    public List<Magic> isEnchanted(ItemStack item) {
        List<Magic> enchants = new ArrayList<Magic>();

        for (Magic magic : MagicList.magic) {
            if (magic.isEnchantedItem(item)) {
                enchants.add(magic);
            }
        }

        if (enchants.size() == 0)
            return null;    
        else 
            return enchants;
    }

    public List<Magic> hasValidBase(List<Magic> custom, ItemStack result, ItemStack left) {
        ItemStack out = result;
        if (out == null) {
            out = left;
        }

        List<Magic> stillValid = new ArrayList<Magic>();
        for(Magic magic : custom) {
            for(BaseType base : magic.getBaseTypes()) {
                if (magic.isValidType(base).contains(out.getType())) {
                    stillValid.add(magic);
                    break;
                }
            }
        }

        return stillValid;
    }

    public List<Magic> removeDuplicates(List<Magic> custom) {
        List<Magic> unique = new ArrayList<Magic>();
        for (Magic magic : custom) {
            if (!unique.contains(magic)) {
                unique.add(magic);
            }
        }

        return unique;
    }

    public ItemStack alterResult(List<Magic> custom, ItemStack result, ItemStack left) {
        ItemStack out = result;
        if (out == null) {
            out = new ItemStack(left.getType(), left.getAmount());
            out.setItemMeta(left.getItemMeta());
        }

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
                    if (out.getEnchantments().containsKey(enchant)) {
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
        ItemMeta meta = out.getItemMeta();
        meta.setLore(lore);
        out.setItemMeta(meta);
        return out;
    }
}
