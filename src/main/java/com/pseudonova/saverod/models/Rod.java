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

    private Ability primaryAbility;
    private Ability secondaryAbility;
    private final List<Ability> passiveAbilities;

    //item's data
    private String displayName;
    private Material material;
    private List<String> lore;

    public Rod(String name) {
        this.name = name;
        this.mustBeHeld = false;


        this.passiveAbilities = new ArrayList<>();

        this.displayName = ChatColor.GREEN + name;
        this.lore = new ArrayList<>();
        this.material = Material.BLAZE_ROD;
    }

    @SuppressWarnings("unchecked")
    public Rod(Map<String, Object> configObject){

        this.name = ChatColor.translateAlternateColorCodes('&', (String) configObject.get("name"));
        this.mustBeHeld = (Boolean) configObject.get("must-be-held");

        //abilities
        this.primaryAbility = (Ability) configObject.get("primary-ability");
        this.secondaryAbility = (Ability) configObject.get("secondary-ability");
        this.passiveAbilities = (List<Ability>) configObject.get("passive-abilities");

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

    public void addPassiveAbility(Ability ability){
        this.passiveAbilities.add(ability);
    }

    public void handleEvent(Event event) {

        this.passiveAbilities.stream()
                .filter(ability -> ability.isSupportedEvent(event))
                .forEach(ability -> ability.handleEvent(event));
    }

    public boolean hasInteractiveAbility() {
        return getPassiveAbilities(AbilityType.INTERACTIVE).size() == 1;
    }

    public <A extends Ability> A getAbility(Class<A> abilityClass){
        return this.passiveAbilities.stream()
                .filter(abilityClass::isInstance)
                .findFirst()
                .map(abilityClass::cast)
                .orElse(null);
    }

    public List<Ability> getPassiveAbilities(AbilityType type){

        return this.passiveAbilities.stream()
                .filter(ability -> ability.getType() == type)
                .collect(Collectors.toList());
    }

    public List<Ability> getPassiveAbilities(){
        return this.passiveAbilities;
    }

    public void setPrimaryAbility(Ability primaryAbility) {
        this.primaryAbility = primaryAbility;
    }

    public void setSecondaryAbility(Ability secondaryAbility) {
        this.secondaryAbility = secondaryAbility;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> serializedObject = new LinkedHashMap<>();

        serializedObject.put("name", this.name);
        serializedObject.put("must-be-held", this.mustBeHeld);
        serializedObject.put("display-name", this.displayName);
        serializedObject.put("lore", this.lore);
        serializedObject.put("material", material.toString());
        serializedObject.put("passive-abilities", this.passiveAbilities);
        serializedObject.put("primary-ability", this.primaryAbility);
        serializedObject.put("secondary-ability", this.secondaryAbility);

        return serializedObject;
    }

    @Override
    public String toString() {
        return "Rod{" +
                "name='" + name + '\'' +
                ", mustBeHeld=" + mustBeHeld +
                ", primaryAbility=" + primaryAbility +
                ", secondaryAbility=" + secondaryAbility +
                ", passiveAbilities=" + passiveAbilities +
                ", displayName='" + displayName + '\'' +
                ", material=" + material +
                ", lore=" + lore +
                '}';
    }

    public Ability getPrimaryAbility() {
        return primaryAbility;
    }

    public Ability getSecondaryAbility() {
        return secondaryAbility;
    }
}
