package com.geoderp.geoextras.Magic;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;

import com.geoderp.geoextras.Magic.Magic.BaseType;

public class MagicList {
    public static ArrayList<Magic> magic = new ArrayList<Magic>();
    public static ArrayList<String> magicStrings;

    public MagicList() {
        magic.add(createHewing());
        magic.add(createForge());
        magic.add(createIllumination());
        magic.add(createDrain());
        magic.add(createDeathWoven());

        magicStrings = getNames();
    }

    public static Magic getMagicByString(String string) {
        for (Magic m : magic) {
            if (m.getName().equals(string)) {
                return m;
            }
        }
        return null;
    }

    public static Magic getMagicByLore(String lore) {
        for (Magic m : magic) {
            if (m.getLore().equals(lore)) {
                return m;
            }
        }
        return null;
    }

    private ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (Magic m : magic) {
            names.add(m.getName());
        }
        return names;
    }

    private Magic createHewing() {
        Magic hewing = new Magic("hewing");
        hewing.setLore("§2Hewing");
        hewing.setBaseTypes(new BaseType[]{BaseType.axe});
        hewing.setConflicts(null);

        return hewing;
    }

    private Magic createForge() {
        Magic forge = new Magic("forge");
        forge.setLore("§6Forge");
        forge.setBaseTypes(new BaseType[]{BaseType.pick});
        Enchantment[] conflicts = {Enchantment.FORTUNE, Enchantment.SILK_TOUCH};
        forge.setConflicts(conflicts);

        return forge;
    }

    private Magic createIllumination() {
        Magic illumination = new Magic("illumination");
        illumination.setLore("§fIllumination");
        illumination.setBaseTypes(new BaseType[]{BaseType.shield});
        illumination.setConflicts(null);

        return illumination;
    }

    private Magic createDrain() {
        Magic drain = new Magic("drain");
        drain.setLore("§4Drain");
        drain.setBaseTypes(new BaseType[]{BaseType.sword, BaseType.axe});
        drain.setConflicts(null);

        return drain;
    }

    private Magic createDeathWoven() {
        Magic deathwoven = new Magic("deathwoven");
        deathwoven.setLore("§8Death Woven");
        deathwoven.setBaseTypes(BaseType.values());
        deathwoven.setConflicts(null);

        return deathwoven;
    }
}
