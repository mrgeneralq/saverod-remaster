package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SaveInventoryAbility extends Ability {

    public SaveInventoryAbility(Rod rod) {
        super(rod, "save-inventory");

        when(PlayerDeathEvent.class, event -> storeInventory(event.getEntity()));
        when(PlayerRespawnEvent.class, event -> setSavedInventory(event.getPlayer()));
    }
    
    private void storeInventory(Player player) {

    }
    private void setSavedInventory(Player player) {

    }
}
