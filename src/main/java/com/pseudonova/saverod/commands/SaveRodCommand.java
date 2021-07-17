package com.pseudonova.saverod.commands;

import com.pseudonova.saverod.interfaces.ICommand;
import com.pseudonova.saverod.interfaces.IConfigService;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class SaveRodCommand implements CommandExecutor {

    private final Map<String, ICommand> subCommands = new HashMap<String, ICommand>();

    public SaveRodCommand(IRodService rodService, IRodInstanceService rodInstanceService, IConfigService configService) {
        this.subCommands.put("rod", new RodCommand(rodService,rodInstanceService));
        this.subCommands.put("reload", new ReloadCommand(configService));
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(args.length == 0) {

            if(!sender.hasPermission("saverod.help")){
                //TODO move all messages to message service and use static messages
                sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to that command!");
                return true;
            }

            sender.sendMessage("Help Menu:");
            this.subCommands.values().stream().map(this::createHelpMessage).forEach(sender::sendMessage);
            return true;
        }

        ICommand command = this.subCommands.get(args[0]);

        if(command == null){
            sender.sendMessage("No subcommand found!");
            return true;
        }

        if(!sender.hasPermission(command.getPermission())){
            sender.sendMessage("No permission");
            return true;
        }

        return command.onCommand(sender, cmd, s, Arrays.copyOfRange(args, 1, args.length));
    }

    private String createHelpMessage(ICommand subCommand){
        return String.format("/rod %s - %s", subCommand.getName(), subCommand.getDescription());
    }
}
