package com.pseudonova.saverod.abilities.healing;

import com.pseudonova.saverod.abilities.abstracts.AbstractHealAbility;

public class SurviveAbility extends AbstractHealAbility
{
    public static final SurviveAbility INSTANCE = new SurviveAbility();

    public SurviveAbility() {
        super("survive", 20);
    }

    @Override
    public String serializeToConfig() {
        return "survive";
    }

    public static SurviveAbility deserialize(String[] parameters){
        return INSTANCE;
    }
}
