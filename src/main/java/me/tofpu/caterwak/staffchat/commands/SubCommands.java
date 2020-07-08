package me.tofpu.caterwak.staffchat.commands;

import org.bukkit.entity.Player;

public interface SubCommands {

    public String getName();

    public String getShortcut();

    public String getPermission();

    public String getDescription();

    public String getSyntax();

    public void perform(Player player, String[] args);

}
