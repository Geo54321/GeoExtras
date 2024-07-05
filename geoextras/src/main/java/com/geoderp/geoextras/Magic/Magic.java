package com.geoderp.geoextras.Magic;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
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
        horse
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
        ItemStack item = new ItemStack(getItemType(this.baseTypes[0]));
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(this.lore);
        item.setItemMeta(meta);

        return item;
    }

    private Material getItemType(BaseType base) {
        switch (base) {
            case pick:
                return Material.DIAMOND_PICKAXE;
            case axe:
                return Material.DIAMOND_AXE;
            case shovel:
                return Material.DIAMOND_SHOVEL;
            case sword:
                return Material.DIAMOND_SWORD;
            case hoe:
                return Material.DIAMOND_HOE;
            case helmet:
                return Material.DIAMOND_HELMET;
            case boots:
                return Material.DIAMOND_BOOTS;
            case chest:
                return Material.DIAMOND_CHESTPLATE;
            case pants:
                return Material.DIAMOND_LEGGINGS;
            case shield:
                return Material.SHIELD;
            case horse:
                return Material.DIAMOND_HORSE_ARMOR;
            default:
                return null;
        }
    }
}
