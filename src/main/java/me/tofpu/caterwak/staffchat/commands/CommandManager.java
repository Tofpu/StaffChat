package me.tofpu.caterwak.staffchat.commands;

import me.tofpu.caterwak.staffchat.StaffChat;
import me.tofpu.caterwak.staffchat.commands.subcommands.Reload;
import me.tofpu.caterwak.staffchat.commands.subcommands.ToggleChat;
import me.tofpu.caterwak.staffchat.commands.subcommands.Whitelist;
import me.tofpu.caterwak.staffchat.utils.ColoredChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommands> subCommands = new ArrayList<>();
    private StaffChat plugin = StaffChat.instance;

    public CommandManager(){
        subCommands.add(new ToggleChat(plugin));
        subCommands.add(new Reload(plugin));
        subCommands.add(new Whitelist(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {

            return true;
        }

        if ((args.length < 1)){
            sender.sendMessage(new ColoredChat("&7&m------&r &e&lStaff&6&lChat &f&lPlugin &6&lV&e&l1.5 &7&m------").getMessage());

            for (SubCommands subCommand : subCommands) {
                sender.sendMessage(String.valueOf(new ColoredChat("&6&l&m|&r &7" + subCommand.getSyntax() + " &f- &7" + subCommand.getDescription()).getMessage()));
            }
            sender.sendMessage(new ColoredChat("&7&m------").getMessage());
            return true;
        }
        for (SubCommands subCommand : subCommands) {
            if (args[0].equalsIgnoreCase((subCommand.getName())) && sender.hasPermission(subCommand.getPermission())) {
                subCommand.perform((Player) sender, args);
            }
        }
        return false;
    }
}
