package com.geoderp.geoextras;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.geoderp.geoextras.Artifacts.*;
import com.geoderp.geoextras.Chat.*;
import com.geoderp.geoextras.Farm.*;
import com.geoderp.geoextras.Misc.*;
import com.geoderp.geoextras.Silly.*;
import com.geoderp.geoextras.Magic.*;
import com.geoderp.geoextras.Magic.Enchants.*;

import java.util.ArrayList;
import java.io.File;

public class Main extends JavaPlugin {
    public FileConfiguration config = getConfig();
    
    @Override
    public void onEnable() {
        // Plugin Setup
        getLogger().info("Geo is here to break things.");
        File pluginDir = new File(getDataFolder() + "/");
        if(!pluginDir.exists()) {
            pluginDir.mkdir();
        }
        loadDefaultConfigFile();
        saveDefaultConfig();

        MagicList ml = new MagicList();
        ml.castMagic();

        // Farm Module
        if (getConfig().getBoolean("modules.farm")) {
            getServer().getPluginManager().registerEvents(new Harvest(), this);
            getServer().getPluginManager().registerEvents(new ExtraGrow(config.getDouble("options.growth-chance-percent")), this);
            getServer().getPluginManager().registerEvents(new Scythe(config.getInt("options.scythe-range")), this);
            getServer().getPluginManager().registerEvents(new Moist(), this);
        }

        // Artifacts Module
        if (getConfig().getBoolean("modules.artifacts")) {
            this.getCommand("geoartifact").setExecutor(new GeoArtifact());
            getServer().getPluginManager().registerEvents(new Magnet(config.getInt("options.weak-magnet-range"),config.getInt("options.strong-magnet-range"),config.getBoolean("options.sneak-disable-magnet")), this);
            getServer().getPluginManager().registerEvents(new Zoomies(), this);
        }

        // Chat Module
        if (getConfig().getBoolean("modules.chat")) {
            this.getCommand("heart").setExecutor(new Heart());
            this.getCommand("rng").setExecutor(new RNG());
            this.getCommand("blamegeo").setExecutor(new BlameGeo());
            this.getCommand("poggers").setExecutor(new Poggers());
        }

        // Misc Module
        if (getConfig().getBoolean("modules.misc")) {
            getServer().getPluginManager().registerEvents(new LapisElevators(config.getInt("options.elevator-range")), this);
            getServer().getPluginManager().registerEvents(new SilkSpawners(), this);
        }

        // Silly Module
        if (getConfig().getBoolean("modules.silly")) {
            this.getCommand("explode").setExecutor(new Explode());
            getServer().getPluginManager().registerEvents(new PissCreepers(), this);
        }

        // Enchantment Module
        if (getConfig().getBoolean("modules.enchantments")) {
            this.getCommand("geomagic").setExecutor(new Caster());
            getServer().getPluginManager().registerEvents(new ConflictChecker(), this);

            getServer().getPluginManager().registerEvents(new Hewing(config.getInt("options.hewing-max-block-break")), this);
            getServer().getPluginManager().registerEvents(new Forge(), this);
            getServer().getPluginManager().registerEvents(new Illumination(), this);
            getServer().getPluginManager().registerEvents(new Drain(), this);
            getServer().getPluginManager().registerEvents(new Deathwoven(), this);
            getServer().getPluginManager().registerEvents(new Prospecting(config.getInt("options.prospecting-max-block-break"), config.getBoolean("options.prospecting-stone-types")), this);
            getServer().getPluginManager().registerEvents(new Quarrying(), this);
        }

        // WIP Module
        if (getConfig().getBoolean("modules.soontm")) {
            getServer().getPluginManager().registerEvents(new MountTeleport(), this);
            getServer().getPluginManager().registerEvents(new JankStep(), this);
        }

        this.getCommand("geoextras").setExecutor(new GeoExtras(this));
    }
    
    @Override
    public void onDisable() {
        getLogger().info("Been a pleasure. Truly.");
    }

    private void loadDefaultConfigFile() {
        ArrayList<String> header = new ArrayList<String>();
        header.add("GeoPlugin Config File");
        config.options().setHeader(header);

        config.addDefault("modules.farm", true);
        config.addDefault("modules.artifacts", true);
        config.addDefault("modules.chat", true);
        config.addDefault("modules.misc", true);
        config.addDefault("modules.silly", true);
        config.addDefault("modules.enchantments", true);
        config.addDefault("modules.soontm", true);
        config.addDefault("options.strong-magnet-range", 4);
        config.addDefault("options.weak-magnet-range", 2);
        config.addDefault("options.sneak-disable-magnet", true);
        config.addDefault("options.growth-chance-percent",0.3);
        config.addDefault("options.scythe-range",4);
        config.addDefault("options.elevator-range",50);
        config.addDefault("options.hewing-max-block-break", 150);
        config.addDefault("options.prospecting-max-block-break", 50);
        config.addDefault("options.prospecting-stone-types", true);

        config.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }
}
