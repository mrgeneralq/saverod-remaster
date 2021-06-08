package com.pseudonova.saverod.factories;

import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.models.Ability;

import java.util.Arrays;

public final class StringAbilityFactory {

    //Example Input: ["heal", "20", "1"]
    public static Ability parseAbility(String[] parameters) throws AbilityParseException {

        String abilityName = parameters[0];
        String[] creationParameters = Arrays.copyOfRange(parameters, 1, parameters.length);

        switch(abilityName.toLowerCase()) {
            case "heal":
                return parseHealAbility(creationParameters);
            default:
                return null;
        }
    }

    public static HealAbility parseHealAbility(String[] creationParameters) throws AbilityParseException {
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
