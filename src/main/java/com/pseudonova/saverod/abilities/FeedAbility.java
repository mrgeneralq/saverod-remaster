package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class FeedAbility extends Ability
{
    private final int foodLevel;

    public FeedAbility(Rod rod, int foodLevel) {
        super(rod, "feed");

        this.foodLevel = foodLevel;

        when(EntityDamageEvent.class, event -> {

            //cancel the event
            event.setCancelled(true);

            Player player = (Player) event.getEntity();

            //the max food level is 20
            int newFoodLevel = Math.min(20, player.getFoodLevel() + this.foodLevel);

            player.setFoodLevel(newFoodLevel);
        });
    }

    public double getFoodLevel() {
        return this.foodLevel;
    }
}
