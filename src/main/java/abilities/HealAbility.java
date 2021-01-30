package abilities;

import models.Ability;
import models.Rod;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class HealAbility extends Ability
{
    private final double healthToHeal;

    public HealAbility(Rod rod, double healthToHeal) {
        super(rod, "heal");

        this.healthToHeal = healthToHeal;
    }

    public double getHealingHealth() {
        return this.healthToHeal;
    }

    @Override
    public void activateWithin(EntityDamageEvent e)
    {
        /// we will already check if it was plaer
        Player player = (Player) e.getEntity();


        double newHealth = Math.min(20, player.getHealth() + this.healthToHeal);

        player.setHealth(newHealth);
    }
}
