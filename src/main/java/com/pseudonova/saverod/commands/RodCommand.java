package com.pseudonova.saverod.commands;

import com.pseudonova.saverod.factories.StringAbilityFactory;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RodCommand implements CommandExecutor {

    private final IRodService rodService;
    private final IRodInstanceService rodInstanceService;
    private final StringAbilityFactory stringAbilityFactory;

    public RodCommand(IRodService rodService, IRodInstanceService rodInstanceService, StringAbilityFactory stringAbilityFactory) {
        this.rodService = rodService;
        this.rodInstanceService = rodInstanceService;
        this.stringAbilityFactory = stringAbilityFactory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player player = (Player) sender;

        switch(args.length) {
            case 1:
            {
                String rodName = args[0];

                if (!rodService.rodExists(rodName)) {
                    sender.sendMessage(ChatColor.RED + "Rod does not exist!");
                    return true;
                }

                Rod rod = rodService.getRodByName(rodName);
                RodInstance rodInstance = rodInstanceService.getNewInstance(rod);


                player.getInventory().addItem(rodService.getRodItem(rodInstance));
                player.sendMessage(ChatColor.WHITE + "You got yourself a magical " + ChatColor.AQUA + rod.getName());
                return true;
            }
            default:
                if(args.length > 2 && args[0].equalsIgnoreCase("addability")){
                    String rodName = args[1];

                    if(!rodService.rodExists(rodName)){
                        sender.sendMessage(ChatColor.RED + "Rod does not exist!");
                        return true;
                    }
                    Rod rod = rodService.getRodByName(rodName);

                    String[] parameters = Arrays.copyOfRange(args, 2, args.length);

                    Ability parsedAbility;

                    try{
                        parsedAbility = this.stringAbilityFactory.parseAbility(parameters);
                    }
                    catch(StringAbilityFactory.AbilityParseException e){
                        player.sendMessage(ChatColor.RED + "FAILED: " + e.getMessage());
                        return true;
                    }
                    rod.addAbility(parsedAbility);
                    player.sendMessage(ChatColor.WHITE + "The Rod " + ChatColor.GREEN + rod.getName() + ChatColor.WHITE + " received the " + ChatColor.YELLOW + parsedAbility.getName() + ChatColor.WHITE + "!");
                    player.getInventory().addItem(this.rodService.getRodItem(rodInstanceService.getNewInstance(rod)));
                }
        }
        return true;
    }
}
