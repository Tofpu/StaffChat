package me.tofpu.caterwak.staffchat.utils;

import org.bukkit.ChatColor;

public class ColoredChat {

    private String prefix;
    private String message;

    private boolean usePrefix = false;

    public ColoredChat(String message){
        this.message = message;
    }

    public String getMessage(){
        return usePrefix ? ChatColor.translateAlternateColorCodes('&', prefix + " " + message) : ChatColor.translateAlternateColorCodes('&', message);
    }

    public void usePrefix(boolean prefix){
        this.usePrefix = prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
