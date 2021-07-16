package com.pseudonova.saverod.eventlisteners.rodinstance;

import com.pseudonova.saverod.events.rodinstance.PreAbilityUseEvent;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PreAbilityUseListener implements Listener {

    @EventHandler
    public void checkUsesLeft(PreAbilityUseEvent event) {

        RodInstance rodInstance = event.getRodInstance();
        Ability ability = event.getAbility();
        Player player = event.getPlayer();

        if(rodInstance.getUsesLeft(ability) == 0) {
            player.sendMessage(ChatColor.RED + String.format("No uses left for the %s ability!", ability.getName()));
            event.setCancelled(true);
        }
    }
}
