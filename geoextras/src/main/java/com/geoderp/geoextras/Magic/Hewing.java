package com.geoderp.geoextras.Magic;

import com.geoderp.geoextras.Magic.Magic.BaseType;

public class Hewing {
    
    public Hewing() {
        Magic hewing = createEnchantment();
    }

    private Magic createEnchantment() {
        Magic hewing = new Magic("hewing");
        hewing.setLore("§2Hewing");
        hewing.setMaxLevel(1);
        hewing.setBaseTypes(new BaseType[]{BaseType.axe});
        hewing.setConflicts(null);

        return hewing;
    }
}
