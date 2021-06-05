package com.pseudonova.saverod.models;

import org.apache.commons.lang.WordUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;
import java.sql.Array;
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

    @SuppressWarnings("unchecked")
    public Rod(Map<String, Object> configObject){

        this.name = (String) configObject.get("name");
        this.displayName = (String) configObject.get("display-name");
        this.mustBeHeld = (Boolean) configObject.get("must-be-held");
        this.material = Material.matchMaterial((String) configObject.get("material"));

        //abilities
        List<String> serializedAbilities = (List<String>) configObject.get("abilities");
        this.abilities = parseAbilities(serializedAbilities);
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
        serializedObject.put("material", material.toString());
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


    private List<Ability> parseAbilities(List<String> abilitiesStrings){
        if(abilitiesStrings == null)
            return new ArrayList<>();

        return abilitiesStrings.stream()
                .map(serializedAbility -> {
                    try {
                        return parseAbility(serializedAbility);
                    }
                    catch(Exception e) {
                        throw new RuntimeException(String.format("Couldn't format the ability '%s'!", serializedAbility));
                    }
                })
                .collect(Collectors.toList());
    }

    private static Ability parseAbility(String configLine) throws ReflectiveOperationException {
        String[] data = configLine.split(" ");
        String abilityName = data[0];
        String[] parameters = Arrays.copyOfRange(data, 1, data.length);

        Class<?> abilityClass = Class.forName(String.format("com.pseudonova.saverod.abilities.%sAbility", WordUtils.capitalizeFully(abilityName)));
        Method deserializerStaticMethod = abilityClass.getDeclaredMethod("deserialize", String[].class);

        return (Ability) deserializerStaticMethod.invoke(null, new Object[]{parameters});
    }

}
