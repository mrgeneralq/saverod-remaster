package abilities;

import models.Ability;
import models.Rod;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class FeedAbility extends Ability
{
    private final int foodLevel;

    public FeedAbility(Rod rod, int foodLevel) {
        super(rod, "feed");

        this.foodLevel = foodLevel;

        supportEvent(EntityDamageEvent.class);
    }

    public double getFoodLevel() {
        return this.foodLevel;
    }

    @Override
    public void activateWithin(Event event)
    {
        EntityDamageEvent damageEvent = (EntityDamageEvent) event;
        Player player = getPlayer(event);

        //cancel the event
        damageEvent.setCancelled(true);

        //the food level is 20
        int newFoodLevel = Math.min(20, player.getFoodLevel() + this.foodLevel);

        player.setFoodLevel(newFoodLevel);
    }

    private Player getPlayer(Event event){
        EntityDamageEvent damageEvent = (EntityDamageEvent) event;

        return (Player) damageEvent.getEntity();
    }
}
