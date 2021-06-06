package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.abilities.abstracts.AbstractHealAbility;

import java.util.Map;

public class SurviveAbility extends AbstractHealAbility
{

    public SurviveAbility(int maxUses) {
        super("survive", 20, maxUses);
    }

    public SurviveAbility(Map<String, Object> map){
        this((int) map.get("max-uses"));
    }

}
