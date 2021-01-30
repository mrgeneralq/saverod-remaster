package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.event.entity.EntityDamageEvent;

public class HealAbility extends Ability
{
    private final double healthToHeal;

    public HealAbility(Rod rod, double healthToHeal) {
        super(rod, "heal");

        this.healthToHeal = healthToHeal;

        when(EntityDamageEvent.class, event -> {

            //reduce the damage by the health to heal
            double newDamage = Math.max(0, event.getDamage() - this.healthToHeal);

            event.setDamage(newDamage);
        });
    }

    public double getHealingHealth() {
        return this.healthToHeal;
    }
}
