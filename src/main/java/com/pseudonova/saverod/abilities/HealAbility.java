package abilities;

import models.Ability;
import models.Rod;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class HealAbility extends Ability
{
    private final double healthToHeal;

    public HealAbility(Rod rod, double healthToHeal) {
        super(rod, "heal");

        this.healthToHeal = healthToHeal;

        supportEvent(EntityDamageEvent.class);
    }

    public double getHealingHealth() {
        return this.healthToHeal;
    }

    @Override
    public void activateWithin(Event event)
    {
       Player player = getPlayer(event);
       double newHealth = Math.min(20, player.getHealth() + this.healthToHeal);

       player.setHealth(newHealth);
    }

    private Player getPlayer(Event event){
        EntityDamageEvent damageEvent = (EntityDamageEvent) event;

        return (Player) damageEvent.getEntity();
    }
}
