package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.enums.AbilityType;
import com.pseudonova.saverod.models.Ability;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class SaveInventoryAbility extends Ability {

    public SaveInventoryAbility() {
        super( "save-inventory", AbilityType.PASSIVE);

        when(PlayerDeathEvent.class, event ->
        {
            event.setKeepInventory(true);
            event.getDrops().clear();
        });
    }

    public SaveInventoryAbility(Map<String, Object> map){
        this();
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>();
    }
}
