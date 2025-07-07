package com.geoderp.geoextras.Disconnect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class DisconnectFileWorker {
    private JavaPlugin plugin;
    private File file;
    private ArrayList<String> messages;
    private BufferedReader reader;

    public DisconnectFileWorker(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messages = new ArrayList<String>();
        file = new File("plugins/GeoExtras/disconnect-messages.txt");
        if(!file.exists()) {
            createFile();
        }
        loadMessages();
    }

    public void createFile() {
        try {
            file.createNewFile();
        }
        catch(IOException e) {
            plugin.getLogger().log(Level.WARNING,"Error creating disconnect messages file: ",e);
        }
    }

    public void loadMessages() {
        try {
            reader = new BufferedReader(new FileReader(file));
        }
        catch(FileNotFoundException e) {
            plugin.getLogger().log(Level.WARNING,"Could not find file: ",e);
        }
        String line;
        try {
            while((line = reader.readLine()) != null)
                messages.add(line);
            reader.close();
        }
        catch(IOException e) {
            plugin.getLogger().log(Level.WARNING,"Error reading file: ",e);
        }
    }

    public void reloadMessages() {
        ArrayList<String> newMessages = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
        }
        catch(FileNotFoundException e) {
            plugin.getLogger().log(Level.WARNING,"Could not find file: ",e);
        }
        String line;
        try {
            while((line = reader.readLine()) != null)
                newMessages.add(line);
            reader.close();
        }
        catch(IOException e) {
            plugin.getLogger().log(Level.WARNING,"Error reading file: ",e);
        }

        messages = newMessages;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
