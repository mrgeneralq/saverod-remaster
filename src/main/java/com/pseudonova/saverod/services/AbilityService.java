package com.pseudonova.saverod.services;

import com.google.common.collect.Lists;
import com.pseudonova.saverod.interfaces.IAbilityService;

import java.util.List;

public class AbilityService implements IAbilityService {

    @Override
    public List<String> getSupportedAbilitiesNames(){
        return Lists.newArrayList("heal", "save-inventory","feed", "save-inventory", "survive");
    }
}
