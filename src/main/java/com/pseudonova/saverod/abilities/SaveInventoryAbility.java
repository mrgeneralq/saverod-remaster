package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SaveInventoryAbility extends Ability {
    public SaveInventoryAbility(Rod rod) {
        super(rod, "save-inventory");

        when(PlayerDeathEvent.class, event -> event.setKeepInventory(true));
    }
}
