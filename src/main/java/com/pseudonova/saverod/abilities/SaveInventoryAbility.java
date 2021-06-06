package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.enums.AbilityType;
import com.pseudonova.saverod.models.Ability;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class SaveInventoryAbility extends Ability {

    public SaveInventoryAbility(int maxUses) {
        super( "save-inventory", AbilityType.PASSIVE, maxUses);

        when(PlayerDeathEvent.class, event ->
        {
            event.setKeepInventory(true);
            event.getDrops().clear();
        });
    }

    public SaveInventoryAbility(Map<String, Object> map){
        this((int) map.get("max-uses"));
    }
}
