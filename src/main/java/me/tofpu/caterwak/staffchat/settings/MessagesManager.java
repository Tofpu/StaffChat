package me.tofpu.caterwak.staffchat.settings;

import me.tofpu.caterwak.staffchat.StaffChat;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginLogger;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessagesManager {

    private File file = null;
    public YamlConfiguration config = null;
    private Logger logger = null;
    ConfigurationSection configurationSection;

    String added = "&eYou have toggled StaffChat &aon&e.";
    String removed = "&eYou have toggled StaffChat &coff&e.";

    private StaffChat staffChat = StaffChat.instance;

    public MessagesManager(StaffChat staffChat){
        this.file = new File(staffChat.getDataFolder(), "messages.yml");

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
    }

    public YamlConfiguration getConfig(){
        return config;
    }

    public void saveConfig() {
        if (config == null){
            this.config = YamlConfiguration.loadConfiguration(file);
        }
        if (file == null){
            this.file = new File(staffChat.getDataFolder(), "messages.yml");
        }
        try {
            config.save(file);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not save config to " + file, ex);
        }
    }

    public void reloadConfig(StaffChat staffChat) {
        if (file == null) {
            this.file = new File(staffChat.getDataFolder(), "messages.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void defaultConfiguration() {
        String message = "messages";
        ConfigurationSection config;
        if (getConfig().getConfigurationSection(message) == null) {
            getConfig().createSection("messages");
        }
        config = getConfig().getConfigurationSection(message);
        if (config.getString("prefix") == null) {
            config.set("prefix", "&8[&4S&6&lC&8] ");
        }
        if (config.getString("toggledOn") == null) {
            config.set("toggledOn", added);
        }
        if (config.getString("toggledOff") == null) {
            config.set("toggledOff", removed);
        }
        if (config.getString("style") == null) {
            config.set("style", "&c%player%: &7%message%");
        }
        saveConfig();
    }

    public String toggledON(){
        String message = "messages";
        ConfigurationSection config = getConfig().getConfigurationSection(message);
        return config.getString("toggledOn");
    }

    public String toggledOFF(){
        String message = "messages";
        ConfigurationSection config = getConfig().getConfigurationSection(message);
        return config.getString("toggledOff");
    }

    public String getPrefix() {
        String message = "messages";
        ConfigurationSection config = getConfig().getConfigurationSection(message);
        return config.getString("prefix");
    }

    public String getStyle() {
        String message = "messages";
        ConfigurationSection config = getConfig().getConfigurationSection(message);
        return config.getString("style");
    }

}
