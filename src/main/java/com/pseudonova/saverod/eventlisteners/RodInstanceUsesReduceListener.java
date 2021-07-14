package com.pseudonova.saverod.eventlisteners;

import com.pseudonova.saverod.events.RodInstanceUsesReduceEvent;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RodInstanceUsesReduceListener implements Listener {


    @EventHandler
    public void onUsesReduce(RodInstanceUsesReduceEvent e){



        RodInstance instance = e.getInstance();
        Ability ability = e.getAbility();
        Player player = e.getPlayer();


        if(instance.getUsesLeft(ability) == 0){
            player.sendMessage("No uses left for ability");
            e.setCancelled(true);
            return;
        }

        //TODO update uses left

    }


}
