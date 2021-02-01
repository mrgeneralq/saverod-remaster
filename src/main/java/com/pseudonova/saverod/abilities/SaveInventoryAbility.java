package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.statics.InventorySnapshot;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SaveInventoryAbility extends Ability {

    private final Map<UUID, InventorySnapshot> playersInventories = new HashMap<>();

    public SaveInventoryAbility(Rod rod) {
        super(rod, "save-inventory");

        when(PlayerDeathEvent.class, event -> {
            event.getDrops().clear();
            storeInventory(event.getEntity());
        });

        when(PlayerRespawnEvent.class, event -> setSavedInventory(event.getPlayer()));
    }
    
    private void storeInventory(Player player) {
        InventorySnapshot snapshot = new InventorySnapshot(player.getInventory());

        this.playersInventories.put(player.getUniqueId(), snapshot);
    }

    private void setSavedInventory(Player player) {
        InventorySnapshot snapshot = this.playersInventories.remove(player.getUniqueId());

        if(snapshot != null)
            snapshot.equip(player.getInventory());
    }
}
