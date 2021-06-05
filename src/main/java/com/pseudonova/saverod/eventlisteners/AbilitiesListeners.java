package com.pseudonova.saverod.eventlisteners;

import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;

import java.util.Arrays;
import java.util.Objects;

public class AbilitiesListeners implements Listener
{
    private final IRodService rodService;

    public AbilitiesListeners(IRodService rodService){
        this.rodService = rodService;
    }

    @EventHandler
    public void onPlayerDamage(PlayerDeathEvent event){

        test(event);
    }

    private void test(Event event) {
        System.out.println("1");
       Player player = getPlayer(event);

        if(player == null)
            return;

        System.out.println("2");

        //try to find a rod in the player's inventory
        Rod rod = Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .map(this.rodService::getRod)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        System.out.println("3");

        rod.activateWithin(event);
    }

    private Player getPlayer(Event event){
        if(event instanceof PlayerEvent) {
            System.out.println("test");
            return ((PlayerEvent) event).getPlayer();
        }
        else if(event instanceof EntityEvent) {
            System.out.println("test 2");
            EntityEvent entityEvent = (EntityEvent) event;

            if (entityEvent.getEntityType() == EntityType.PLAYER)
                return (Player) entityEvent.getEntity();
        }
        return null;
    }
}
