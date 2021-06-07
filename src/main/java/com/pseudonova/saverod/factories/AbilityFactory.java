package com.pseudonova.saverod.factories;

import com.google.common.collect.Lists;
import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.interfaces.factories.IAbilityFactory;

import java.util.ArrayList;
import java.util.List;

public class AbilityFactory implements IAbilityFactory {

    private static final List<String> ABILITIES_NAMES = getAbilitiesNames();


    public AbilityFactory(){

    }

    @Override
    public HealAbility newHealAbility(int healthToHeal, int maxUses){
        return new HealAbility(healthToHeal, maxUses);
    }


    private static List<String> getAbilitiesNames(){
        return Lists.newArrayList(
                "heal", "save-inventory","feed", "save-inventory", "survive");

    }
}
