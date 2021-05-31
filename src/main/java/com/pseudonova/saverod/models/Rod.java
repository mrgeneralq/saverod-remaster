package com.pseudonova.saverod.models;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class Rod {

    private final String name;
    private boolean mustBeHeld = false;
    private List<Ability> abilities = new ArrayList<>();

    //item's data
    private String displayName = "";
    private Material material = Material.BLAZE_ROD;
    private List<String> lore = new ArrayList<>();

    public Rod(String name) {
        this.name = name;
    }


    public Rod(Map<String, Object> configObject){
        this.name = (String) configObject.get("name");
        this.mustBeHeld = (boolean) configObject.get("must-be-held");
        this.displayName = (String) configObject.get("display-name");
        this.material = Material.getMaterial((String) configObject.get("material"));
        this.lore = (List<String>) configObject.get("lore");
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
                .forEach(ability -> ability.activateWithin(event));
    }


    public Map<String, Object> serialize() {

        Map<String, Object> serializedObject = new HashMap<>();

        serializedObject.put("name", this.name);
        serializedObject.put("display-name", this.displayName);
        serializedObject.put("must-be-held", this.mustBeHeld);
        serializedObject.put("lore", this.lore);
        serializedObject.put("material", this.material.toString());
        serializedObject.put("abilities", this.abilities.stream().map(Ability::serializeToConfig).collect(Collectors.toList()));

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
