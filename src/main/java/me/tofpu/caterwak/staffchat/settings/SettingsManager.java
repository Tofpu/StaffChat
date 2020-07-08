package me.tofpu.caterwak.staffchat.settings;

import me.tofpu.caterwak.staffchat.StaffChat;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginLogger;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsManager {

    private File file = null;
    public YamlConfiguration config = null;
    private Logger logger = null;

    private StaffChat staffChat = StaffChat.instance;

    public SettingsManager(StaffChat staffChat){
        this.file = new File(staffChat.getDataFolder(), "settings.yml");

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

    public void defaultConfigurations(){
        if (getConfig().options().header() == null){
            getConfig().options().header("\n" +
                    "  _________ __          _____  ______________ .__            __   \n" +
                    " /   _____//  |______ _/ ____\\/ ____\\_   ___ \\|  |__ _____ _/  |_ \n" +
                    " \\_____  \\\\   __\\__  \\\\   __\\\\   __\\/    \\  \\/|  |  \\\\__  \\\\   __\\\n" +
                    " /        \\|  |  / __ \\|  |   |  |  \\     \\___|   Y  \\/ __ \\|  |  \n" +
                    "/_______  /|__| (____  /__|   |__|   \\______  /___|  (____  /__|  \n" +
                    "        \\/           \\/                     \\/     \\/     \\/   \n");
        }
        if (getConfig().getConfigurationSection("settings") == null) {
            getConfig().createSection("settings");
        }
        if (getConfig().getConfigurationSection("settings").get("usePrefix") == null) {
            getConfig().getConfigurationSection("settings").set("usePrefix", true);
        }
        saveConfig();
    }

    public YamlConfiguration getConfig(){
        return config;
    }

    public void saveConfig() {
        if (config == null){
            this.config = YamlConfiguration.loadConfiguration(file);
        }
        if (file == null){
            this.file = new File(staffChat.getDataFolder(), "settings.yml");
        }
        try {
            config.save(file);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not save config to " + file, ex);
        }
    }

    public void reloadConfig(StaffChat staffChat) {
        if (file == null) {
            this.file = new File(staffChat.getDataFolder(), "settings.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public String getPrefix() {
        return staffChat.messagesManager.getPrefix();
    }

    public String getStyle() {
        return staffChat.messagesManager.getStyle();
    }

    public boolean usePrefix() {
        return getConfig().getConfigurationSection("settings").getBoolean("usePrefix");
    }

}
