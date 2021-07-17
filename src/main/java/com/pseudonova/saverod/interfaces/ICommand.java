package com.pseudonova.saverod.interfaces;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommand {
    boolean onCommand(CommandSender commandSender, Command command, String commandName, String[] args);
    String getPermission();
    String getName();
    String getDescription();
}
