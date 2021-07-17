package com.pseudonova.saverod.commands;

import com.pseudonova.saverod.abstracts.AbstractCommand;
import com.pseudonova.saverod.commands.rod.RodCreateCommand;
import com.pseudonova.saverod.commands.rod.RodRemoveCommand;
import com.pseudonova.saverod.interfaces.ICommand;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RodCommand extends AbstractCommand {

    private final IRodService rodService;
    private final IRodInstanceService rodInstanceService;
    private final Map<String, ICommand> subCommands = new HashMap<>();

    public RodCommand(IRodService rodService, IRodInstanceService rodInstanceService) {
        super("rod", "rod", "rod command");

        this.rodService = rodService;
        this.rodInstanceService = rodInstanceService;

        this.subCommands.put("create", new RodCreateCommand(rodService));
        this.subCommands.put("remove", new RodRemoveCommand(rodService));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {

        if(args.length == 0) {
            sender.sendMessage("Help Menu:");
            this.subCommands.values().stream().map(this::createHelpMessage).forEach(sender::sendMessage);
            return true;
        }

        ICommand subCommand = this.subCommands.get(args[0]);

        if(command == null){
            sender.sendMessage("No subcommand found!");
            return true;
        }

        if(!sender.hasPermission(subCommand.getPermission())){
            sender.sendMessage("No permission");
            return true;
        }

        return subCommand.onCommand(sender, command, commandName, Arrays.copyOfRange(args, 1, args.length));
    }

    private String createHelpMessage(ICommand subCommand){
        return String.format("/rod %s - %s", subCommand.getName(), subCommand.getDescription());
    }

}
