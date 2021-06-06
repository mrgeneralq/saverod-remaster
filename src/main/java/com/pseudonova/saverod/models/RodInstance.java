package com.pseudonova.saverod.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

@SuppressWarnings("unchecked")
public class RodInstance implements ConfigurationSerializable {

    private final UUID instanceID;
    private final String rodID;
    private Map<String, Integer> usesLeft;

    public RodInstance(Map<String, Object> map){
        this.instanceID =  UUID.fromString((String) map.get("id"));
        this.rodID = (String) map.get("rod");
        this.usesLeft = (Map<String, Integer>) map.get("uses-left");
    }

    public RodInstance(Rod rod) {
        this.instanceID = UUID.randomUUID();
        this.rodID = rod.getName();
        this.usesLeft = new HashMap<>();
    }

    public UUID getInstanceID() {
        return instanceID;
    }

    public String getRodID() {
        return this.rodID;
    }

    public Integer getUsesLeft(String abilityName) {
        return usesLeft.get(abilityName);
    }

    public void setUsesLeft(Map<String, Integer> usesLeft) {
        this.usesLeft = usesLeft;
    }

    //TODO to be moved to RodService



    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", this.instanceID.toString());
        map.put("rod", this.rodID);
        map.put("uses-left", this.usesLeft);
        return map;
    }

}
