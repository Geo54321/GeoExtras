package com.geoderp.geoextras;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.geoderp.geoextras.Artifacts.*;
import com.geoderp.geoextras.Chat.*;
import com.geoderp.geoextras.Farm.*;
import com.geoderp.geoextras.Misc.*;
import com.geoderp.geoextras.Silly.*;

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

        // Farm Module
        if (getConfig().getBoolean("modules.farm")) {
            getServer().getPluginManager().registerEvents(new Harvest(), this);
            getServer().getPluginManager().registerEvents(new ExtraGrow(this), this);
            getServer().getPluginManager().registerEvents(new Scythe(this), this);
        }

        // Artifacts Module
        if (getConfig().getBoolean("modules.artifacts")) {
            this.getCommand("geoartifact").setExecutor(new GeoArtifact());
            getServer().getPluginManager().registerEvents(new Magnet(this), this);
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
        }

        // Silly Module
        if (getConfig().getBoolean("modules.silly")) {
            this.getCommand("explode").setExecutor(new Explode());
            getServer().getPluginManager().registerEvents(new PissCreepers(), this);
        }

        // Enchantment Module
        if (getConfig().getBoolean("modules.enchantments")) {
            // Deathwoven -- soulbound
            // Drain -- leech
            // Forge -- autosmelt
            // Hewing -- felling
            // Quarrying -- hammer
            // Paring -- leaf removal
            // Illumination -- shield auto-place torches
            // Faithful -- mount tp on death
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
        config.addDefault("options.hewing-max-block-break", 50);

        config.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }
}
