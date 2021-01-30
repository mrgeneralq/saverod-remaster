package abilities;

import models.Ability;
import models.Rod;
import org.bukkit.entity.Player;

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
    public void activate(Player player)
    {
        //the maximum hp is 20
        double newHealth = Math.min(20, player.getHealth() + this.healthToHeal);

        player.setHealth(newHealth);
    }
}
