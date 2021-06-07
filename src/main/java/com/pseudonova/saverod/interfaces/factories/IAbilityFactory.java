package com.pseudonova.saverod.interfaces.factories;

import com.pseudonova.saverod.abilities.HealAbility;

public interface IAbilityFactory {
    HealAbility newHealAbility(int healthToHeal, int maxUses);
}
