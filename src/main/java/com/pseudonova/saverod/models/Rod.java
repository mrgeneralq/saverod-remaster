package com.pseudonova.saverod.models;

import com.pseudonova.saverod.enums.AbilityType;
import com.pseudonova.saverod.statics.NameSpaceCollector;
import org.apache.commons.lang.WordUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
        this.displayName = "reetstok";
        this.mustBeHeld = false;
        this.abilities = new ArrayList<>();
        this.lore = new ArrayList<>();
        this.material = Material.BLAZE_ROD;
    }

    //just a constructor of the object with a map containing the config properties

    @SuppressWarnings("unchecked")
    public Rod(Map<String, Object> configObject){

        this.name = (String) configObject.get("name");
        this.displayName = (String) configObject.get("display-name");
        this.mustBeHeld = (Boolean) configObject.get("must-be-held");
        this.material = Material.matchMaterial((String) configObject.get("material"));
        this.abilities = (List<Ability>) configObject.get("abilities");
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
                .forEach(ability ->
                {
                    Bukkit.broadcastMessage(ability.getName() + " ability was used, inside the rod " + this.name);
                    ability.activateWithin(event);
                });
    }

    public boolean hasInteractiveAbility() {
        return getAbilities(AbilityType.INTERACTIVE).size() == 1;
    }

    public List<Ability> getAbilities(AbilityType type){
        return this.abilities.stream()
                .filter(ability -> ability.getType() == type)
                .collect(Collectors.toList());
    }


    public Map<String, Object> serialize() {

        Map<String, Object> serializedObject = new HashMap<>();

        serializedObject.put("name", this.name);
        serializedObject.put("display-name", this.displayName);
        serializedObject.put("must-be-held", this.mustBeHeld);
        serializedObject.put("lore", this.lore);
        serializedObject.put("material", material.toString());
        serializedObject.put("abilities", this.abilities);

        return serializedObject;
    }

    public ItemStack getItem(){

        ItemStack itemStack = new ItemStack(this.material);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));
        meta.getPersistentDataContainer().set(NameSpaceCollector.getInstance().getRodKey(), PersistentDataType.STRING, this.name);

        meta.setLore(this.abilities.stream().map(Ability::getName).collect(Collectors.toList()));

        itemStack.setItemMeta(meta);

        return itemStack;

    }
}
