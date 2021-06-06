package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.abilities.abstracts.AbstractHealAbility;

import java.util.Map;

public class HealAbility extends AbstractHealAbility
{
    public HealAbility(double healthToHeal, int maxUses) {
        super("heal", healthToHeal, maxUses);
    }

    public HealAbility(Map<String, Object> map){
        this((double) map.get("health-to-heal"), (int) map.get("max-uses"));
    }
}
