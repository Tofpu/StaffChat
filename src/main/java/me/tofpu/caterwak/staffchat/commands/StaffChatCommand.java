package me.tofpu.caterwak.staffchat.commands;

import me.tofpu.caterwak.staffchat.StaffChat;
import me.tofpu.caterwak.staffchat.Module.StaffChatManager;
import me.tofpu.caterwak.staffchat.settings.SettingsManager;
import me.tofpu.caterwak.staffchat.utils.ColoredChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.tofpu.caterwak.staffchat.settings.WhitelistedManager.getWhitelisted;

public class StaffChatCommand extends SettingsManager implements CommandExecutor {

    // [SC] [PlayerName]: <message>

    private StaffChat plugin = StaffChat.instance;

    String prefix = usePrefix() ? getPrefix() : "";

    public StaffChatCommand(StaffChat staffChat) {
        super(staffChat);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(new ColoredChat("&cThis is a player command.").getMessage());
            return true;
        }

        Player player = (Player) sender;

        if ((player.hasPermission("staffchat.use") || player.isOp() || player.getUniqueId().equals(getWhitelisted()))){

            if (args.length >= 1){
                String arg = "";

                for(String i : args){
                    arg = i;
                }

                new StaffChatManager(plugin, player.getName(), arg);

            } else {
                player.sendMessage(new ColoredChat(prefix + "" + "&cInvalid Argument. /staffchat [message]").getMessage());
            }
        } else {
            player.sendMessage(new ColoredChat(prefix + "&cYou do not have the permission to execute this command.").getMessage());
        }
        return false;
    }

}
