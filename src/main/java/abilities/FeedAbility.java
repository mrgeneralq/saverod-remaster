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
    }

    public double getFoodLevel() {
        return this.foodLevel;
    }

    @Override
    public void activateWithin(Event e)
    {
        //here is where I most likely need your abstraction powers

        if(!(e instanceof EntityDamageEvent))
            return;

        // this will only run when the event type was Damage, so we can still set food levels

        EntityDamageEvent event = (EntityDamageEvent) e;

        event.setCancelled(true);



        //the food level is 20
        int newFoodLevel = Math.min(20, player.getFoodLevel() + this.foodLevel);

        player.setFoodLevel(newFoodLevel);
    }
}
