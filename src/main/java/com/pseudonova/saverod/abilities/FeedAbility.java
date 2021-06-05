package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.enums.AbilityType;
import com.pseudonova.saverod.models.Ability;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;

public class FeedAbility extends Ability {
    private final int foodLevel;

    public FeedAbility(int foodLevel) {
        super( "feed", AbilityType.INTERACTIVE);

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

    //if I do something wrong, please tell

    public FeedAbility(Map<String, Object> map){
        this((int) map.get("feed-amount"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("feed-amount", this.foodLevel);

        return map;
    }
}
