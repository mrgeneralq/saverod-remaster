package com.pseudonova.saverod.commands;

import com.pseudonova.saverod.abstracts.AbstractCommand;
import com.pseudonova.saverod.interfaces.IConfigService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends AbstractCommand {

    private final IConfigService configService;

    protected ReloadCommand(IConfigService configService) {
        super("reload", "reload", "reload the configuration files and data");
        this.configService = configService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {

        configService.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "config reloaded!");
        return true;
    }
}
