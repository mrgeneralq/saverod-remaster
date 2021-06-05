package com.pseudonova.saverod.abilities.abstracts;

import com.pseudonova.saverod.models.Ability;
import org.bukkit.event.entity.EntityDamageEvent;

public abstract class AbstractHealAbility extends Ability
{

    protected final double healthToHeal;

    public AbstractHealAbility(double healthToHeal, String name) {
        super(name);

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
