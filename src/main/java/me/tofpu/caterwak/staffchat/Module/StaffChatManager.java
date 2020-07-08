package me.tofpu.caterwak.staffchat.Module;

import me.clip.placeholderapi.PlaceholderAPI;
import me.tofpu.caterwak.staffchat.StaffChat;
import me.tofpu.caterwak.staffchat.settings.SettingsManager;
import me.tofpu.caterwak.staffchat.utils.ColoredChat;
import org.bukkit.Bukkit;

import static me.tofpu.caterwak.staffchat.settings.WhitelistedManager.getWhitelisted;

public class StaffChatManager extends SettingsManager {

    private boolean usePrefix = getConfig().getConfigurationSection("settings").getBoolean("usePrefix");

    private String format = new ColoredChat(usePrefix ? getPrefix() + getStyle() : getStyle()).getMessage();

    private String message = "";
    private int subString = 0;

    public StaffChatManager(StaffChat staffChat, String display, String message){
        super(staffChat);
        if (!this.message.equals("")){
            message = this.message;
        }
        StringBuilder finalMessage = new StringBuilder();
        finalMessage.append(message.substring(subString)).append(" ");
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.hasPermission("staffchat.use") || p.getUniqueId().equals(getWhitelisted())){
                if (isPlaceholderHooked()){
                    p.sendMessage(PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(display), format).replace("%player%", display).replace("%message%", finalMessage));
                    return;
                }
                p.sendMessage(new ColoredChat(format).getMessage().replace("%player%", display).replace("%message%", finalMessage));
            }
        });
    }

    public boolean isPlaceholderHooked(){
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

//    public StaffChatManager(StaffChat staffChat, String display, String message){
//        super(staffChat);
//        Bukkit.getOnlinePlayers().forEach(p -> {
//            if (p.hasPermission("staffchat.use")){
//                p.sendMessage(new ColoredChat(String.format(format, display, message)).getMessage());
//            }
//        });
//    }

}
