package me.tofpu.caterwak.staffchat.commands.subcommands;

import me.tofpu.caterwak.staffchat.StaffChat;
import me.tofpu.caterwak.staffchat.commands.SubCommands;
import me.tofpu.caterwak.staffchat.settings.MessagesManager;
import me.tofpu.caterwak.staffchat.utils.ColoredChat;
import org.bukkit.entity.Player;

public class Reload extends MessagesManager implements SubCommands {

    private StaffChat plugin = StaffChat.instance;

    public Reload(StaffChat staffChat) {
        super(staffChat);
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getShortcut() {
        return "r";
    }

    @Override
    public String getPermission() {
        return "staffchat.reload";
    }

    @Override
    public String getDescription() {
        return "Reloads whitelist.yml, settings.yml and messages.yml&r&8&m!";
    }

    @Override
    public String getSyntax() {
        return "/msc &f" + getName();
    }

    @Override
    public void perform(Player player, String[] args) {
        plugin.messagesManager.reloadConfig(plugin);
        plugin.whitelistedManager.reloadConfig(plugin);
        plugin.settingsManager.reloadConfig(plugin);

        player.sendMessage(new ColoredChat(getPrefix() + "&7You have successfully reloaded &fmessages.yml, &fsettings.yml &7and &fwhitelist.yml.").getMessage());
    }
}
