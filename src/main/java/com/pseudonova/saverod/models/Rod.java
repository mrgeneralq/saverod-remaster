package com.pseudonova.saverod.models;

import com.google.common.collect.Lists;
import com.pseudonova.saverod.enums.AbilityType;

import org.apache.commons.lang.Validate;
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
    private final boolean mustBeHeld;
    private final List<Ability> passiveAbilities;
    private Ability primaryAbility, secondaryAbility;
    private ItemStack baseItem;

    private Rod(Builder builder) {
        this.name = builder.name;
        this.mustBeHeld = builder.mustBeHeld;
        this.primaryAbility = builder.primaryAbility;
        this.secondaryAbility = builder.secondaryAbility;
        this.passiveAbilities = builder.passiveAbilities;
        this.baseItem = builder.baseItem;
    }

    @SuppressWarnings("unchecked")
    public Rod(Map<String, Object> configObject){

        this.name = ChatColor.translateAlternateColorCodes('&', (String) configObject.get("name"));
        this.mustBeHeld = (Boolean) configObject.get("must-be-held");
        this.primaryAbility = (Ability) configObject.get("primary-ability");
        this.secondaryAbility = (Ability) configObject.get("secondary-ability");
        this.passiveAbilities = (List<Ability>) configObject.get("passive-abilities");

        //item
        this.baseItem = new ItemStack(Material.matchMaterial((String) configObject.get("material")));
        ItemMeta meta = this.baseItem.getItemMeta();
        meta.setDisplayName((String) configObject.get("display-name"));
        meta.setLore((List<String>) configObject.get("lore"));
        this.baseItem.setItemMeta(meta);
    }

    public static Rod createDefault(String name){
        ItemStack defaultBaseItem = createDefaultItem(name);

        return new Rod.Builder()
                .named(name)
                .withBaseItem(defaultBaseItem)
                .build();
    }

    public String getName() {
        return name;
    }

    public boolean mustBeHeld(){
        return this.mustBeHeld;
    }

    public ItemStack getBaseItem(){
        return this.baseItem.clone();
    }

    public void setBaseItem(ItemStack baseItem){
        this.baseItem = baseItem;
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

    public Ability getPrimaryAbility() {
        return primaryAbility;
    }

    public Ability getSecondaryAbility() {
        return secondaryAbility;
    }

    public List<Ability> getPassiveAbilities(){
        return this.passiveAbilities;
    }

    public List<Ability> getAbilities(){
        List<Ability> abilities = Lists.newArrayList(this.primaryAbility, this.secondaryAbility);
        abilities.addAll(this.passiveAbilities);

        return abilities;
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
        serializedObject.put("display-name", this.baseItem.getItemMeta().getDisplayName());
        serializedObject.put("lore", this.baseItem.getItemMeta().getLore());
        serializedObject.put("material", this.baseItem.getType().toString());
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
                ", displayName='" + this.baseItem.getItemMeta().getDisplayName() + '\'' +
                ", material=" + this.baseItem.getType() +
                ", lore=" + this.baseItem.getItemMeta().getLore() +
                '}';
    }

    private static ItemStack createDefaultItem(String name){
        ItemStack baseItem = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = baseItem.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        meta.setLore(new ArrayList<>());
        baseItem.setItemMeta(meta);

        return baseItem;
    }

    public static class Builder {
        private String name;
        private boolean mustBeHeld = false;
        private ItemStack baseItem;
        private Ability primaryAbility, secondaryAbility;
        private List<Ability> passiveAbilities = new ArrayList<>();

        public Builder named(String name){
            this.name = name;
            return this;
        }
        public Builder mustBeHeld(){
            this.mustBeHeld = true;
            return this;
        }
        public Builder withBaseItem(ItemStack baseItem){
            Validate.isTrue(baseItem.hasItemMeta() && baseItem.getItemMeta().hasLore(), "The rod's base item must have lore!");
            this.baseItem = baseItem;
            return this;
        }
        public Builder withPrimaryAbility(Ability primaryAbility){
            this.primaryAbility = primaryAbility;
            return this;
        }
        public Builder withSecondaryAbility(Ability secondaryAbility){
            this.secondaryAbility = secondaryAbility;
            return this;
        }
        public Builder withPassiveAbility(Ability ability){
            this.passiveAbilities.add(ability);
            return this;
        }
        public Rod build() {
            Validate.notNull(this.name, "The rod must have a name!");
            Validate.notNull(this.baseItem, "The rod's base item must be provided!");

            return new Rod(this);
        }
    }
}
