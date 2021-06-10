package com.pseudonova.saverod.models;

import com.pseudonova.saverod.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class RodInstance implements ConfigurationSerializable {

    public static final String ROD_IDENTIFIER = "rod-id: ";

    private final String instanceID;

    private Rod rod;
    private String rodID;

    private Map<String, Integer> usesLeft;

    public RodInstance(Map<String, Object> map){
        this.instanceID = (String) map.get("id");
        this.rodID = (String) map.get("rodID");
        this.usesLeft = (Map<String, Integer>) map.get("uses-left");
    }

    public RodInstance(Rod rod) {

        this.instanceID = createRandomInstanceID();
        this.rodID = rod.getName();
        this.usesLeft = new HashMap<>();

    }

    public String getInstanceID() {
        return instanceID;
    }

    public String getRodID() {
        return this.rodID;
    }

    public Integer getUsesLeft(Ability ability) {
        String abilityName = ability.getName();
        return usesLeft.get(abilityName);
    }

    //also did this
    public void setUsesLeft(Ability ability, int usesLeft){
        this.usesLeft.put(ability.getName(), usesLeft);
    }

    public void setUsesLeft(Map<String, Integer> usesLeft) {
        this.usesLeft = usesLeft;
    }

    public void reduceUsesleft(Ability ability){
        String abilityName = ability.getName();

        if(this.usesLeft.get(abilityName) == 0)
            return;

        this.usesLeft.merge(abilityName, 1, Math::subtractExact);
    }


    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", this.instanceID);
        map.put("rodID", this.rodID);
        map.put("uses-left", this.usesLeft);
        return map;
    }

    private static String createRandomInstanceID(){
        return UUID.randomUUID().toString().substring(0, 7).replace("-", "");
    }

    public void setRod(Rod rod) {
        this.rod = rod;
        this.rodID = rod.getName();
    }

    public ItemStack getItem() {

        ItemStack itemStack = new ItemStack(rod.getMaterial());

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(rod.getDisplayName());

        List<String> lore = getLoreWithAbilities();
        lore.add(ChatColor.AQUA + ROD_IDENTIFIER + ChatColor.GOLD + this.getInstanceID());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;

    }

    public List<String> getLoreWithAbilities(){

        List<String> newLore = new ArrayList<>(this.rod.getLore());
        newLore.add("");

        //primary ability
        Ability primaryAbility = this.rod.getPrimaryAbility();

        if(primaryAbility != null)
            newLore.add(String.format(ChatColor.GOLD + "Primary Ability: %s " + ChatColor.AQUA + " (%s/%s)",primaryAbility.getName(), this.getUsesLeft(primaryAbility), rod.getPrimaryAbility().getMaxUses()));

        newLore.add(ChatColor.GRAY + "Abilities(" + ChatColor.GREEN + this.rod.getPassiveAbilities().size() + ChatColor.GRAY + "):");

        newLore.addAll(rod.getPassiveAbilities().stream().map(Ability::getName).map(abilityName -> ChatColor.GREEN + abilityName).collect(Collectors.toList()));

        return newLore;
    }

    public Rod getRod() {
        return rod;
    }
}
