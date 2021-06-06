package com.pseudonova.saverod.abilities;

import com.pseudonova.saverod.abilities.abstracts.AbstractHealAbility;

import java.util.HashMap;
import java.util.Map;

public class SurviveAbility extends AbstractHealAbility
{
    public SurviveAbility() {
        super("survive", 20);
    }

    public SurviveAbility(Map<String, Object> map){
        this();
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>();
    }
}
