package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.abilities.abstracts.AbstractHealAbility;

public class HealAbility extends AbstractHealAbility
{
    public HealAbility(Double healthToHeal) {
        super("heal", healthToHeal);
    }
}
