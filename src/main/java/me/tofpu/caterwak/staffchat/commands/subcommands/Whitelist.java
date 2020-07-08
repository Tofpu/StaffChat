package me.tofpu.caterwak.staffchat.commands.subcommands;

import me.tofpu.caterwak.staffchat.StaffChat;
import me.tofpu.caterwak.staffchat.commands.SubCommands;
import me.tofpu.caterwak.staffchat.settings.MessagesManager;
import me.tofpu.caterwak.staffchat.settings.WhitelistedManager;
import me.tofpu.caterwak.staffchat.utils.ColoredChat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.tofpu.caterwak.staffchat.settings.WhitelistedManager.getWhitelisted;

public class Whitelist extends MessagesManager implements SubCommands {

    private StaffChat plugin = StaffChat.instance;

    public Whitelist(StaffChat staffChat) {
        super(staffChat);
    }

    @Override
    public String getName() {
        return "whitelist";
    }

    @Override
    public String getShortcut() {
        return "wl";
    }

    @Override
    public String getPermission() {
        return "staffchat.wl";
    }

    @Override
    public String getDescription() {
        return "Whitelist a player&r&8&m!";
    }

    @Override
    public String getSyntax() {
        return "/msc &f" + getName() + " &7[add/remove] &f<player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if ((args.length != 3)){
            player.sendMessage(new ColoredChat(getPrefix() + "&cInvalid Argument. /wastaffchat &7[add/remove] &f<player>").getMessage());
            return;
        }

        WhitelistedManager whitelistedManager = plugin.whitelistedManager;
        if (args[1].equalsIgnoreCase("add")) {
            if (isOnline(args[2])) {
                Player target = Bukkit.getPlayer(args[2]);
                UUID targetUniqueId = target.getUniqueId();
                if (targetUniqueId.equals(getWhitelisted())) {
                    player.sendMessage(new ColoredChat(getPrefix() + "&c" + args[2] + " &eis already added to the whitelist.").getMessage());
                    return;
                }
                whitelistedManager.addPlayer(targetUniqueId);
                player.sendMessage(new ColoredChat(getPrefix() + "&eYou have successfully added &c" + target.getName()).getMessage());
            } else if (!isOnline(args[2])) {
                if (Bukkit.getOfflinePlayer(args[2]).hasPlayedBefore()) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                    UUID targetUniqueId = target.getUniqueId();
                    if (targetUniqueId.equals(getWhitelisted())) {
                        player.sendMessage(new ColoredChat(getPrefix() + "&c" + args[2] + " &eis already added to the whitelist.").getMessage());
                        return;
                    }
                    whitelistedManager.addPlayer(targetUniqueId);
                    player.sendMessage(new ColoredChat(getPrefix() + "&eYou have successfully added &c" + target.getName()).getMessage());
                } else {
                    player.sendMessage(new ColoredChat(getPrefix() + "&c" + args[2] + " &ehas not joined the server before.").getMessage());
                }
            }
        } else if (args[1].equalsIgnoreCase("remove")){
            if (isOnline(args[2])) {
                Player target = Bukkit.getPlayer(args[2]);
                UUID targetUniqueId = target.getUniqueId();
                if (!targetUniqueId.equals(getWhitelisted())) {
                    player.sendMessage(new ColoredChat(getPrefix() + "&c" + args[2] + " &eis not in the whitelist.").getMessage());
                    return;
                }
                whitelistedManager.removePlayer(targetUniqueId);
                player.sendMessage(new ColoredChat(getPrefix() + "&eYou have successfully removed &c" + target.getName()).getMessage());
            } else if (!isOnline(args[2])) {
                if (Bukkit.getOfflinePlayer(args[2]).hasPlayedBefore()) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                    UUID targetUniqueId = target.getUniqueId();
                    if (!targetUniqueId.equals(getWhitelisted())) {
                        player.sendMessage(new ColoredChat(getPrefix() + "&c" + args[2] + " &eis not in the whitelist.").getMessage());
                        return;
                    }
                    whitelistedManager.removePlayer(targetUniqueId);
                    player.sendMessage(new ColoredChat(getPrefix() + "&eYou have successfully removed &c" + target.getName()).getMessage());
                } else {
                    player.sendMessage(new ColoredChat(getPrefix() + "&c" + args[2] + " &ehas not joined the server before.").getMessage());
                }
            }
        }
    }

    public boolean isOnline(String name){
        if (Bukkit.getPlayer(name) == null){
            return false;
        } else return Bukkit.getPlayer(name).isOnline();
    }

}
