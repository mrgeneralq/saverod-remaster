package com.pseudonova.saverod.eventlisteners;

import com.pseudonova.saverod.models.Rod;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class AbilitiesListeners implements Listener
{
    private final Rod testRod;

    public AbilitiesListeners(Rod testRod){
        this.testRod = testRod;
    }

    @EventHandler
    public void on(EntityDamageEvent event){

        //activate the rod only if the damaged entity is a player
        if(event.getEntityType() == EntityType.PLAYER)
            this.testRod.activateWithin(event);
    }

    @EventHandler
    public void on(PlayerDeathEvent event){
        this.testRod.activateWithin(event);
    }

    @EventHandler
    public void on(PlayerRespawnEvent event){
        this.testRod.activateWithin(event);
    }

    //Testings, don't touch

    /*private void activateRod(Event event){

        if(this.)


        Player player = null;

        if(event instanceof PlayerEvent)
            player = ((PlayerEvent) event).getPlayer();

        else if(event instanceof EntityDamageEvent){
            EntityDamageEvent entityDamageEvent = (EntityDamageEvent) event;

            if(entityDamageEvent.getEntityType() == EntityType.PLAYER)
                player = (Player) ((EntityDamageEvent) event).getEntity();
        }

        if(player == null)
            return;

        this.testRod.activateWithin(event);
    }*/
}
