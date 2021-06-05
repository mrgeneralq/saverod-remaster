package com.pseudonova.saverod.abilities.abstracts;

import com.pseudonova.saverod.models.Ability;
import org.bukkit.event.entity.EntityDamageEvent;

public abstract class AbstractHealAbility extends Ability
{
    protected final double healthToHeal; //protected because it's required for serialization

    public AbstractHealAbility(String name, double healthToHeal) {
        super(name); //this class is abstract, it doesn't even define the name of the ability. only the HEAL related stuff. do you see?

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
