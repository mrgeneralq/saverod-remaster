package com.pseudonova.saverod.commands.rod;

import com.pseudonova.saverod.abstracts.AbstractCommand;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class RodCreateCommand extends AbstractCommand {

    private final IRodService rodService;


    public RodCreateCommand(IRodService rodService) {
        super("create", "rod.create", "create a new rod");
        this.rodService = rodService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {

        //no name specified
        if(args.length == 0){
            sender.sendMessage(ChatColor.RED + "Please specify the name of the rod");
            return true;
        }

        String rodName = args[0];

        if(rodService.rodExists(rodName)){
            sender.sendMessage(ChatColor.RED + "This rod already exists");
            return true;
        }

        Rod rod = new Rod(rodName);
        rod.setDisplayName(rodName);

        rodService.createRod(rod);
        sender.sendMessage(ChatColor.GREEN + String.format("%s created", rod.getName()));
        return true;
    }
}
