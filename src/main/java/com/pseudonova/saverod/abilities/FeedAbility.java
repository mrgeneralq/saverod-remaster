package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class FeedAbility extends Ability {
    private final int foodLevel;

    public FeedAbility(int foodLevel) {
        super( "feed");

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

    @Override
    public String serializeToConfig(){
        return String.format("feed %d", this.foodLevel);
    }

    public static FeedAbility deserialize(String[] parameters){
        int foodLevel = Integer.parseInt(parameters[0]);

        return new FeedAbility(foodLevel);
    }

    /*
    @Override
    public boolean hasValidParameters(String[] args) {

        if (args.length == 0)
            return false;

        try {
            int valueToFeed = Integer.parseInt(args[0]);
            if (valueToFeed > 0 && valueToFeed <= 20)
                return true;
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
     */
}
