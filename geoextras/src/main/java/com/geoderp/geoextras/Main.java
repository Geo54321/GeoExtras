package com.geoderp.geoextras;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.geoderp.geoextras.Artifacts.*;
import com.geoderp.geoextras.Chat.*;
import com.geoderp.geoextras.Farm.*;
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

        // Jank Module
        if (getConfig().getBoolean("modules.jank")) {
            getServer().getPluginManager().registerEvents(new MountTeleport(), this);
            this.getCommand("explode").setExecutor(new Explode());
            getServer().getPluginManager().registerEvents(new JankStep(), this);
            getServer().getPluginManager().registerEvents(new PissCreepers(), this);
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
        config.addDefault("modules.jank", true);
        config.addDefault("modules.enchantments", true);
        config.addDefault("options.strong-magnet-range", 4);
        config.addDefault("options.weak-magnet-range", 2);
        config.addDefault("options.sneak-disable-magnet", true);
        config.addDefault("options.growth-chance-percent",0.3);
        config.addDefault("options.scythe-range",4);
        config.addDefault("options.hewing-max-block-break", 50);

        config.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }
}
