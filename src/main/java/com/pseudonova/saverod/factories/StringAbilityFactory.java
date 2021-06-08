package com.pseudonova.saverod.factories;

import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.models.Ability;

import java.util.Arrays;

public class StringAbilityFactory {

    //Example Input: ["heal", "20", "1"]
    public Ability parseAbility(String[] parameters) throws AbilityParseException {
        System.out.println("parameters: " + Arrays.toString(parameters));
        String abilityName = parameters[0];
        System.out.println("abilityName: " + abilityName);
        String[] creationParameters = Arrays.copyOfRange(parameters, 1, parameters.length);

        switch(abilityName.toLowerCase()) {
            case "heal":
                return newHealAbility(creationParameters);
            default:
                return null;
        }
    }

    public HealAbility newHealAbility(String[] creationParameters) throws AbilityParseException {
        verifyParametersAmount("heal", creationParameters, 2);

        int healthToHeal = Integer.parseInt(creationParameters[0]);
        int maxUses = Integer.parseInt(creationParameters[1]);

        return new HealAbility(healthToHeal, maxUses);
    }

    private static void verifyParametersAmount(String abilityName, String[] creationParameters, int expectedAmount){
        if(creationParameters.length != expectedAmount)
            throw new AbilityParseException(String.format("The %s ability requires 2 parameters to create(%s provided)!", abilityName, creationParameters.length));
    }

    public static class AbilityParseException extends RuntimeException {

        public AbilityParseException(String message){
            super(message);
        }
    }
}
