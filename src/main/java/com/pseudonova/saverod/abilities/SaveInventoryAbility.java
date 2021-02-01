package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.statics.InventoryContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SaveInventoryAbility extends Ability {

    private final Map<UUID, InventoryContainer> playersInventories = new HashMap<>();

    public SaveInventoryAbility(Rod rod) {
        super(rod, "save-inventory");

        when(PlayerDeathEvent.class, event -> {
            event.getDrops().clear();
            storeInventory(event.getEntity());
        });

        when(PlayerRespawnEvent.class, event -> setSavedInventory(event.getPlayer()));
    }
    
    private void storeInventory(Player player) {
        InventoryContainer container = new InventoryContainer(player.getInventory());

        this.playersInventories.put(player.getUniqueId(), container);
    }

    private void setSavedInventory(Player player) {
        InventoryContainer container = this.playersInventories.remove(player.getUniqueId());

        if(container != null)
            container.equip(player.getInventory());
    }
}
