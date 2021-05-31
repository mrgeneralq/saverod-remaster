package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.event.entity.EntityDamageEvent;

public class HealAbility extends Ability
{
    private final double healthToHeal;

    public HealAbility(double healthToHeal) {
        super("heal");

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

    public static HealAbility deserialize(String[] abilityString){

        double healthToHeal = Double.parseDouble(abilityString[0]);

        return new HealAbility(healthToHeal);

    }

}
