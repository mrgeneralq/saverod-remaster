package com.pseudonova.saverod.models;

import org.bukkit.Material;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class Rod {

    private final String name = "";
    private boolean mustBeHeld;
    private final List<Ability> abilities = new ArrayList<>();

    //item's data
    private String displayName = "";
    private Material material = Material.BLAZE_ROD;
    private List<String> lore = new ArrayList<>();

    public Rod(String displayName, boolean mustBeHeld) {
        this.displayName = displayName;
        this.mustBeHeld = mustBeHeld;
    }

    public String getName() {
        return name;
    }

    public boolean mustBeHeld(){
        return this.mustBeHeld;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void addAbility(Ability ability){
        this.abilities.add(ability);
    }

    public void activateWithin(Event event) {

        this.abilities.stream()
                .filter(ability -> ability.isSupportedEvent(event))
                .forEach(ability -> ability.activateWithin(event));
    }
}
