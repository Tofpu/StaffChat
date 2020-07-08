package me.tofpu.caterwak.staffchat.listeners;

import me.tofpu.caterwak.staffchat.Module.StaffChatManager;
import me.tofpu.caterwak.staffchat.StaffChat;
import me.tofpu.caterwak.staffchat.commands.subcommands.ToggleChat;
import me.tofpu.caterwak.staffchat.settings.SettingsManager;
import me.tofpu.caterwak.staffchat.utils.ColoredChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static me.tofpu.caterwak.staffchat.settings.WhitelistedManager.getWhitelisted;

public class ChatListener extends SettingsManager implements Listener {

    private StaffChat plugin = StaffChat.instance;

    String style1 = getConfig().getConfigurationSection("settings").getString("style");

    boolean usePrefix = getConfig().getConfigurationSection("settings").getBoolean("usePrefix");

    String format = usePrefix ? getPrefix() + style1 : style1;

    public ChatListener(StaffChat staffChat) {
        super(staffChat);
    }

    @EventHandler
    public void onShortcut(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String message = e.getMessage();

        if (message.startsWith("#")) {
            if (!(player.hasPermission("staffchat.use") || player.isOp() || player.getUniqueId().equals(getWhitelisted()))){
                player.sendMessage(new ColoredChat(getPrefix() + "&cYou do not have the permission to execute this command.").getMessage());
                return;
            }
            e.setCancelled(true);

            new StaffChatManager(plugin, player.getName(), message.substring(1));
        } else if (ToggleChat.toggleChat.contains(player.getUniqueId())){
            e.setCancelled(true);

            new StaffChatManager(plugin, player.getName(), message);
        }
    }

}
