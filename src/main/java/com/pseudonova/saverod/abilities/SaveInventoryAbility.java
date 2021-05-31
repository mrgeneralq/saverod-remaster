package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SaveInventoryAbility extends Ability {
    public SaveInventoryAbility() {
        super( "save-inventory");

        when(PlayerDeathEvent.class, event -> event.setKeepInventory(true));
    }
}
