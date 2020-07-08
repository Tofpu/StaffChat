package me.tofpu.caterwak.staffchat.commands.subcommands;

import me.tofpu.caterwak.staffchat.StaffChat;
import me.tofpu.caterwak.staffchat.commands.SubCommands;
import me.tofpu.caterwak.staffchat.settings.MessagesManager;
import me.tofpu.caterwak.staffchat.settings.SettingsManager;
import me.tofpu.caterwak.staffchat.utils.ColoredChat;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ToggleChat extends MessagesManager implements SubCommands {

    private StaffChat plugin = StaffChat.instance;
    private SettingsManager settings = plugin.settingsManager;

    public static ArrayList<UUID> toggleChat = new ArrayList<>();

    String formatAdded = settings.usePrefix() ? new ColoredChat(getPrefix() + toggledON()).getMessage() : new ColoredChat(toggledON()).getMessage();
    String formatRemoved = settings.usePrefix() ? new ColoredChat(getPrefix() + toggledOFF()).getMessage() : new ColoredChat(toggledOFF()).getMessage();

    public ToggleChat(StaffChat staffChat) {
        super(staffChat);
    }

    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public String getShortcut() {
        return "t";
    }

    @Override
    public String getPermission() {
        return "staffchat.toggle";
    }

    @Override
    public String getDescription() {
        return "Toggles the staff chat&r&8&m!";
    }

    @Override
    public String getSyntax() {
        return "/msc &f" + getName();
    }

    @Override
    public void perform(Player player, String[] args) {
        UUID uuid = player.getUniqueId();

        if (toggleChat.contains(uuid)){
            toggleChat.remove(uuid);
        } else {
            toggleChat.add(uuid);
        }
        player.sendMessage(toggleChat.contains(uuid) ? formatAdded : formatRemoved);
    }
}
