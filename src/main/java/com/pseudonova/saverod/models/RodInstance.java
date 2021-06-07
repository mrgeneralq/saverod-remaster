package com.pseudonova.saverod.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.*;

@SuppressWarnings("unchecked")
public class RodInstance implements ConfigurationSerializable {

    private final String instanceID;
    private final String rodID;
    private Map<String, Integer> usesLeft;

    public RodInstance(Map<String, Object> map){
        this.instanceID = (String) map.get("id");
        this.rodID = (String) map.get("rod");
        this.usesLeft = (Map<String, Integer>) map.get("uses-left");
    }

    public RodInstance(Rod rod) {

        this.instanceID = UUID.randomUUID().toString().substring(0, 7).replace("-", "");
        this.rodID = rod.getName();
        this.usesLeft = new HashMap<>();

    }

    public String getInstanceID() {
        return instanceID;
    }

    public String getRodID() {
        return this.rodID;
    }

    public Integer getUsesLeft(Ability ability) {
        String abilityName = ability.getName();

        return usesLeft.get(abilityName);
    }

    public void setUsesLeft(Map<String, Integer> usesLeft) {
        this.usesLeft = usesLeft;
    }

    //TODO to be moved to RodService



    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", this.instanceID);
        map.put("rod", this.rodID);
        map.put("uses-left", this.usesLeft);
        return map;
    }

}
