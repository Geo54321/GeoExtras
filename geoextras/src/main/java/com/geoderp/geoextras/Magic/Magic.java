package com.geoderp.geoextras.Magic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Magic {
    private String name;
    private ArrayList<String> lore;
    private BaseType[] baseTypes;
    private Enchantment[] conflictEnchants;
    
    enum BaseType {
        pick,
        axe,
        shovel,
        sword,
        hoe,
        helmet,
        boots,
        chest,
        pants,
        shield,
        horse,
        bow,
        book
    }

    public Magic(String name) {
        this.name = name;
        this.lore = new ArrayList<String>();
    }

    public String getName() {
        return this.name;
    }

    public String getLore() {
        return this.lore.getFirst();
    }

    public BaseType[] getBaseTypes() {
        return this.baseTypes;
    }

    public Enchantment[] getConflicts() {
        return this.conflictEnchants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLore(String lore) {
        this.lore.add(lore);
    }

    public void setBaseTypes(BaseType[] baseTypes) {
        this.baseTypes = baseTypes;
    }

    public void setConflicts(Enchantment[] conflictEnchants) {
        this.conflictEnchants = conflictEnchants;
    }

    public ItemStack getMagicedItem() {
        ItemStack item = new ItemStack(Material.BOOK);
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(this.lore);
        item.setItemMeta(meta);

        return item;
    }

    public boolean isMagicItem(ItemStack item) {
        if (item != null) {
            if (!item.getType().equals(Material.BOOK)) {
                if (item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if(meta.hasLore()) {
                        List<String> lore = meta.getLore();
                        for (String line : lore) {
                            if (line.equals(this.lore.get(0))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isEnchantedItem(ItemStack item) {
        if (item != null) {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    for (String line : lore) {
                        if (line.equals(this.lore.get(0))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void damageItem(ItemStack item, int amount) {
        // Unbreaking check and reductions
        if (item.containsEnchantment(Enchantment.UNBREAKING)) {
            int level = item.getEnchantmentLevel(Enchantment.UNBREAKING);
            int count = 0;
            for (int g = 0; g < amount; g++) {
                if ((Math.random()*100) > (100/(level+1))) {
                    count++;
                }
            }
            amount = count;
        }

        // Reduce durability
        Damageable dmg = (Damageable) item.getItemMeta();
        dmg.setDamage(dmg.getDamage() + amount);
        item.setItemMeta(dmg);
    }

    public List<Material> isValidType(BaseType base) {
        List<Material> validMats = new ArrayList<Material>();
        switch (base) {
            case pick:
                validMats.add(Material.NETHERITE_PICKAXE);
                validMats.add(Material.DIAMOND_PICKAXE);
                validMats.add(Material.IRON_PICKAXE);
                break;
            case axe:
                validMats.add(Material.NETHERITE_AXE);
                validMats.add(Material.DIAMOND_AXE);
                validMats.add(Material.IRON_AXE);
                break;
            case shovel:
                validMats.add(Material.NETHERITE_SHOVEL);
                validMats.add(Material.DIAMOND_SHOVEL);
                validMats.add(Material.IRON_SHOVEL);
                break;
            case sword:
                validMats.add(Material.NETHERITE_SWORD);
                validMats.add(Material.DIAMOND_SWORD);
                validMats.add(Material.IRON_SWORD);
                validMats.add(Material.MACE);
                validMats.add(Material.TRIDENT);
                break;
            case hoe:
                validMats.add(Material.NETHERITE_HOE);
                validMats.add(Material.DIAMOND_HOE);
                validMats.add(Material.IRON_HOE);
                break;
            case helmet:
                validMats.add(Material.NETHERITE_HELMET);
                validMats.add(Material.DIAMOND_HELMET);
                validMats.add(Material.IRON_HELMET);
                break;
            case boots:
                validMats.add(Material.NETHERITE_BOOTS);
                validMats.add(Material.DIAMOND_BOOTS);
                validMats.add(Material.IRON_BOOTS);
                break;
            case chest:
                validMats.add(Material.NETHERITE_CHESTPLATE);
                validMats.add(Material.DIAMOND_CHESTPLATE);
                validMats.add(Material.IRON_CHESTPLATE);
                break;
            case pants:
                validMats.add(Material.NETHERITE_LEGGINGS);
                validMats.add(Material.DIAMOND_LEGGINGS);
                validMats.add(Material.IRON_LEGGINGS);
                break;
            case shield:
                validMats.add(Material.SHIELD);
                break;
            case horse:
                validMats.add(Material.LEATHER_HORSE_ARMOR);
                validMats.add(Material.IRON_HORSE_ARMOR);
                validMats.add(Material.GOLDEN_HORSE_ARMOR);
                validMats.add(Material.DIAMOND_HORSE_ARMOR);
                break;
            case bow:
                validMats.add(Material.BOW);
                validMats.add(Material.CROSSBOW);
                break;
            case book:
                validMats.add(Material.BOOK);
                break;
            default:
                break;
        }

        return validMats;
    }
}
