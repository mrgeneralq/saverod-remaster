package com.pseudonova.saverod.abilities.healing;

import com.pseudonova.saverod.abilities.abstracts.AbstractHealAbility;

public class HealAbility extends AbstractHealAbility
{

    public HealAbility(Double healthToHeal) {
        super(healthToHeal,"heal");
    }

    @Override
    public String serializeToConfig(){
        //changed to %s instead %f as this is required to parse it back to double
        return String.format("heal %s", this.healthToHeal);
    }

    public static HealAbility deserialize(String[] parameters){
        double healthToHeal = Double.parseDouble(parameters[0]);
        return new HealAbility(healthToHeal);
    }

}
