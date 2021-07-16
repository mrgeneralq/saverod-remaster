package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.enums.AbilityType;
import com.pseudonova.saverod.models.Ability;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;

public class FeedAbility extends Ability {
    private final int foodLevel;

    public FeedAbility(int foodLevel, int maxUses) {
        super( "feed", AbilityType.INTERACTIVE, maxUses);

        this.foodLevel = foodLevel;

        when(EntityDamageEvent.class, event -> {

            //cancel the event
            event.setCancelled(true);

            Player player = (Player) event.getEntity();
            int newFoodLevel = Math.min(20, player.getFoodLevel() + this.foodLevel);

            player.setFoodLevel(newFoodLevel);
        });
    }

    public FeedAbility(Map<String, Object> map){
        this((int) map.get("feed-amount"), (int) map.get("max-uses"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("feed-amount", this.foodLevel);
        return map;
    }
}
