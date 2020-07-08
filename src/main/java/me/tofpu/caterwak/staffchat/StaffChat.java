package me.tofpu.caterwak.staffchat;

import me.tofpu.caterwak.staffchat.commands.CommandManager;
import me.tofpu.caterwak.staffchat.commands.StaffChatCommand;
import me.tofpu.caterwak.staffchat.listeners.ChatListener;
import me.tofpu.caterwak.staffchat.settings.MessagesManager;
import me.tofpu.caterwak.staffchat.settings.SettingsManager;
import me.tofpu.caterwak.staffchat.settings.WhitelistedManager;
import me.tofpu.caterwak.staffchat.utils.ColoredChat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffChat extends JavaPlugin {

    public static StaffChat instance;
    public SettingsManager settingsManager = new SettingsManager(this);
    public MessagesManager messagesManager = new MessagesManager(this);
    public WhitelistedManager whitelistedManager = new WhitelistedManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getConsoleSender().sendMessage("\n" +
                "  _________ __          _____  ______________ .__            __   \n" +
                " /   _____//  |______ _/ ____\\/ ____\\_   ___ \\|  |__ _____ _/  |_ \n" +
                " \\_____  \\\\   __\\__  \\\\   __\\\\   __\\/    \\  \\/|  |  \\\\__  \\\\   __\\\n" +
                " /        \\|  |  / __ \\|  |   |  |  \\     \\___|   Y  \\/ __ \\|  |  \n" +
                "/_______  /|__| (____  /__|   |__|   \\______  /___|  (____  /__|  \n" +
                "        \\/           \\/                     \\/     \\/     \\/   ");
        Bukkit.getConsoleSender().sendMessage(new ColoredChat("&8[&4S&6&lC&8] " + "Version: 1.0").getMessage());
        Bukkit.getConsoleSender().sendMessage(new ColoredChat("&8[&4S&6&lC&8] " + "Author: Tofpu").getMessage());
        Bukkit.getConsoleSender().sendMessage(new ColoredChat(isPlaceholderHooked() ? "StaffChat has Successfully hooked into PlaceHolderAPI" : "StaffChat Couldn't find PlaceHolderAPI").getMessage());
        configChecks();
        registerCommands();
        registerEvents();
    }

    public void configChecks(){
        settingsManager.saveConfig();
        settingsManager.defaultConfigurations();
        whitelistedManager.saveConfig();
        whitelistedManager.defaultConfiguration();
        messagesManager.saveConfig();
        messagesManager.defaultConfiguration();
    }
    
    public void registerCommands(){
        Bukkit.getPluginCommand("staffchat").setExecutor(new StaffChatCommand(this));
        Bukkit.getPluginCommand("sc").setExecutor(new StaffChatCommand(this));

        getCommand("msc").setExecutor(new CommandManager());

//        Bukkit.getPluginCommand("astaffchat").setExecutor(new ReloadCommand());
//        Bukkit.getPluginCommand("asc").setExecutor(new ReloadCommand());

//        Bukkit.getPluginCommand("staffchattoggle").setExecutor(new ToggleChatCommand(this));
//        Bukkit.getPluginCommand("sctoggle").setExecutor(new ToggleChatCommand(this));

//        Bukkit.getPluginCommand("wstaffchat").setExecutor(new WhitelistComand(this));
//        Bukkit.getPluginCommand("wsc").setExecutor(new WhitelistComand(this));
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }

    public boolean isPlaceholderHooked(){
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }
}
