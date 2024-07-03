package com.geoderp.geoextras.Magic;

import org.bukkit.enchantments.Enchantment;

public class Magic {
    private String name;
    private String lore;
    private int maxLevel;
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
        armor,
        pants,
        shield,
        horse
    }

    public Magic(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getLore() {
        return this.lore;
    }

    public int getMaxLevel() {
        return this.maxLevel;
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
        this.lore = lore;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setBaseTypes(BaseType[] baseTypes) {
        this.baseTypes = baseTypes;
    }

    public void setConflicts(Enchantment[] conflictEnchants) {
        this.conflictEnchants = conflictEnchants;
    }
}
