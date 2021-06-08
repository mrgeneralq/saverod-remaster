package com.pseudonova.saverod.models;

import com.pseudonova.saverod.enums.AbilityType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Event;

import java.util.*;
import java.util.stream.Collectors;

public class Rod implements ConfigurationSerializable {

    private final String name;
    private final boolean mustBeHeld;
    private final List<Ability> abilities;

    //item's data
    private String displayName;
    private Material material;
    private List<String> lore;

    public Rod(String name) {
        this.name = name;
        this.mustBeHeld = false;
        this.abilities = new ArrayList<>();

        this.displayName = ChatColor.GREEN + name;
        this.lore = new ArrayList<>();
        this.material = Material.BLAZE_ROD;
    }

    @SuppressWarnings("unchecked")
    public Rod(Map<String, Object> configObject){

        this.name = ChatColor.translateAlternateColorCodes('&', (String) configObject.get("name"));
        this.mustBeHeld = (Boolean) configObject.get("must-be-held");
        this.abilities = (List<Ability>) configObject.get("abilities");

        this.displayName = (String) configObject.get("display-name");
        this.material = Material.matchMaterial((String) configObject.get("material"));
        this.lore = (List<String>) configObject.get("lore");
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

    public void handleEvent(Event event) {

        this.abilities.stream()
                .filter(ability -> ability.isSupportedEvent(event))
                .forEach(ability -> ability.handleEvent(event));
    }

    public boolean hasInteractiveAbility() {
        return getAbilities(AbilityType.INTERACTIVE).size() == 1;
    }

    public <A extends Ability> A getAbility(Class<A> abilityClass){
        return this.abilities.stream()
                .filter(abilityClass::isInstance)
                .findFirst()
                .map(abilityClass::cast)
                .orElse(null);
    }

    public List<Ability> getAbilities(AbilityType type){

        return this.abilities.stream()
                .filter(ability -> ability.getType() == type)
                .collect(Collectors.toList());
    }

    public List<Ability> getAbilities(){
        return this.abilities;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> serializedObject = new LinkedHashMap<>();

        serializedObject.put("name", this.name);
        serializedObject.put("must-be-held", this.mustBeHeld);
        serializedObject.put("display-name", this.displayName);
        serializedObject.put("lore", this.lore);
        serializedObject.put("material", material.toString());
        serializedObject.put("abilities", this.abilities);

        return serializedObject;
    }
}
