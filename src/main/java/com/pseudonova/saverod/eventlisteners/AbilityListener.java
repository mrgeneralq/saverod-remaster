package com.pseudonova.saverod.eventlisteners;

import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.statics.InventoryUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbilityListener implements Listener
{
    private final IRodService rodService;

    public AbilityListener(IRodService rodService){
        this.rodService = rodService;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        handle(event);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        handle(event);
    }

    private void handle(Event event) {

       Player player = getPlayer(event);

       //if a player wasn't involved in the event, do nothing
        if(player == null)
            return;

        //activate the rods in the player's inventory
        for(Rod rod : getRodsIn(player.getInventory()))
            rod.handleEvent(event);
    }

    private List<Rod> getRodsIn(Inventory inventory){
        return InventoryUtils.getItems(inventory).stream()
                .map(this.rodService::getRod)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Player getPlayer(Event event){
        if(event instanceof PlayerEvent) {
            return ((PlayerEvent) event).getPlayer();
        }
        else if(event instanceof EntityEvent) {
            EntityEvent entityEvent = (EntityEvent) event;

            if (entityEvent.getEntityType() == EntityType.PLAYER)
                return (Player) entityEvent.getEntity();
        }
        return null;
    }
}
