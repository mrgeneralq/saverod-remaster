package com.pseudonova.saverod.abilities;

public class SurviveAbility extends HealAbility
{
    public static final SurviveAbility INSTANCE = new SurviveAbility();

    public SurviveAbility() {
        super(20);
    }

    @Override
    public String serializeToConfig() {
        return "survive";
    }

    public static SurviveAbility deserialize(String[] parameters){
        return INSTANCE;
    }
}
