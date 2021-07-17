package com.pseudonova.saverod.commands.rod;

import com.pseudonova.saverod.abstracts.AbstractCommand;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RodRemoveCommand extends AbstractCommand {

    private final IRodService rodService;


    public RodRemoveCommand(IRodService rodService) {
        super("remove", "rod.remove", "Remove an existing rod");
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

        if(!rodService.rodExists(rodName)){
            sender.sendMessage(ChatColor.RED + "This rod does not exist!");
            return true;
        }

        Rod rod = new Rod(rodName);
        rod.setDisplayName(rodName);

        rodService.createRod(rod);
        sender.sendMessage(ChatColor.GREEN + String.format("%s has been removed", rod.getName()));
        return true;
    }
}
