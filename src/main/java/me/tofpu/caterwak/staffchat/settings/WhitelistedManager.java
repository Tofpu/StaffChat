package me.tofpu.caterwak.staffchat.settings;

import me.tofpu.caterwak.staffchat.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhitelistedManager {

    private static ArrayList<UUID> whitelisted = new ArrayList<>();

    private File file = null;
    public YamlConfiguration config = null;
    private Logger logger = null;
    ConfigurationSection configurationSection;

    private StaffChat staffChat = StaffChat.instance;

    public WhitelistedManager(StaffChat staffChat){
        this.file = new File(staffChat.getDataFolder(), "whitelist.yml");

        if (!staffChat.getDataFolder().exists()){
            staffChat.getDataFolder().mkdirs();
        }

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
        this.logger = new PluginLogger(staffChat);
            for(String i : config.getConfigurationSection("players").getKeys(false)) {
                whitelisted.add(UUID.fromString(config.getConfigurationSection("players").getString(i)));
                Bukkit.getConsoleSender().sendMessage(config.getConfigurationSection("players").getString(i));
            }
    }

    public YamlConfiguration getConfig(){
        return config;
    }

    public void saveConfig() {
        if (config == null){
            this.config = YamlConfiguration.loadConfiguration(file);
        }
        if (file == null){
            this.file = new File(staffChat.getDataFolder(), "whitelist.yml");
        }
        try {
            config.save(file);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not save config to " + file, ex);
        }
    }

    public void reloadConfig(StaffChat staffChat) {
        if (file == null) {
            this.file = new File(staffChat.getDataFolder(), "whitelist.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void defaultConfiguration(){
        if (config.getConfigurationSection("players") == null){
            config.createSection("players");
        }
    }

    public static UUID getWhitelisted(){
        for (UUID uuid : whitelisted) {
            return uuid;
        }
        return null;
    }

    public void addPlayer(UUID uuid){
        whitelisted.add(uuid);
        config.set("players." + Bukkit.getPlayer(uuid).getName(), uuid.toString());
        saveConfig();
    }

    public void removePlayer(UUID uuid){
        whitelisted.remove(uuid);
        config.set("players." + Bukkit.getPlayer(uuid).getName(), null);
        saveConfig();
    }

}
