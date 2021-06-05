package com.pseudonova.saverod.models;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class Rod implements ConfigurationSerializable {

    private final String name;
    private boolean mustBeHeld;
    private List<Ability> abilities;

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


    public Rod(Map<String, Object> configObject){

        this.name = (String) configObject.get("name");
        this.displayName = (String) configObject.get("display-name");
        this.mustBeHeld = (Boolean) configObject.get("must-be-held");
        this.material = (Material) Material.matchMaterial((String) configObject.get("material"));

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


    public Map<String, Object> serialize() {

        Map<String, Object> serializedObject = new HashMap<>();

        serializedObject.put("name", this.name);
        serializedObject.put("display-name", this.displayName);
        serializedObject.put("must-be-held", this.mustBeHeld);
    //    serializedObject.put("lore", this.lore);
        serializedObject.put("material", material.toString());
    //    serializedObject.put("abilities", this.abilities.stream().map(Ability::serializeToConfig).collect(Collectors.toList()));

        return serializedObject;
    }

    public ItemStack getItem(){

        ItemStack itemStack = new ItemStack(this.material);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));

        meta.setLore(this.abilities.stream().map(Ability::getName).collect(Collectors.toList()));

        itemStack.setItemMeta(meta);

        return itemStack;

    }

}
