package com.pseudonova.saverod.commands;

import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RodCommand implements CommandExecutor {

    private final IRodService rodService;
    private final IRodInstanceService rodInstanceService;

    public RodCommand(IRodService rodService, IRodInstanceService rodInstanceService) {
        this.rodService = rodService;
        this.rodInstanceService = rodInstanceService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        //TEST CODE NOT FINAL

        if(args.length != 1) return true;

        String subCommand = args[0];

        if(!rodService.rodExists(subCommand)){
            sender.sendMessage(ChatColor.RED + "Rod does not exist!");
            return true;
        }

        Rod rod = rodService.getRodByName(subCommand);

        RodInstance rodInstance = rodInstanceService.getNewInstance(rod);

        Player player = (Player) sender;
        player.getInventory().addItem(rodService.getRodItem(rodInstance));
        player.sendMessage("You got yourself a " + rod.getName());
        return true;
    }
}
