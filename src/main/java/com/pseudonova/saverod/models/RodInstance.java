package com.pseudonova.saverod.models;

import com.pseudonova.saverod.enums.AbilityType;
import com.pseudonova.saverod.statics.NameSpaceCollector;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.stream.Collectors;

public class RodInstance implements ConfigurationSerializable {

    private final UUID instanceID;
    private final Rod rod;
    private Map<Ability, Integer> usesLeft = new HashMap<>();


    public RodInstance(Rod rod) {
        this.instanceID = UUID.randomUUID();
        this.rod = rod;
    }

    public UUID getInstanceID() {
        return instanceID;
    }

    public Rod getRod() {
        return rod;
    }

    public Map<Ability, Integer> getUsesLeft() {
        return usesLeft;
    }

    public void setUsesLeft(Map<Ability, Integer> usesLeft) {
        this.usesLeft = usesLeft;
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }


    //item's data

}
