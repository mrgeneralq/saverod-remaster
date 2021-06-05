package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SaveInventoryAbility extends Ability {

    public static final SaveInventoryAbility INSTANCE = new SaveInventoryAbility();

    public SaveInventoryAbility() {
        super( "save-inventory");

        when(PlayerDeathEvent.class, event -> event.setKeepInventory(true));
    }

    @Override
    public String serializeToConfig(){
        return "save-inventory";
    }

    public static SaveInventoryAbility deserialize(String[] parameters){
        return INSTANCE;
    }
}
