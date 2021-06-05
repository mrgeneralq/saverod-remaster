package com.pseudonova.saverod.commands;

import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RodCommand implements CommandExecutor {

    private final IRodService rodService;

    public RodCommand(IRodService rodService) {
        this.rodService = rodService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        //TEST CODE NOT FINAL

        if(args.length != 1) return true;

        String subCommand = args[0];

        if(!rodService.rodExists(subCommand)){
            sender.sendMessage("Rod does not exist!");
            return true;
        }

        Rod rod = rodService.getRodByName(subCommand);

        Player player = (Player) sender;
        player.getInventory().addItem(rod.getItem());
        player.sendMessage("You got yourself a " + rod.getDisplayName());

        return true;
    }
}
