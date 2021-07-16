package com.pseudonova.saverod.models;
import com.pseudonova.saverod.events.rodinstance.AbilityUseEvent;
import com.pseudonova.saverod.events.rodinstance.PreAbilityUseEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;


public class RodInstance {

    private final String id;
    private transient Rod rod;
    private String rodID;
    private ItemStack rodItem;
    private Map<String, Integer> usesLeft;

    public RodInstance(Rod rod, String id, ItemStack rodItem) {
        this.id = id;
        this.rod = rod;
        this.rodID = rod.getName();
        this.rodItem = rodItem;
        this.usesLeft = new HashMap<>();
    }

    public ItemStack getRodItem(){
        return this.rodItem;
    }

    public String getID(){
        return this.id;
    }

    public Rod getRod() {
        return rod;
    }

    public String getRodID() {
        return this.rodID;
    }

    public Integer getUsesLeft(Ability ability) {
        String abilityName = ability.getName();

        return usesLeft.getOrDefault(abilityName, 0);
    }

    public void setUsesLeft(Ability ability, int usesLeft) {
        this.usesLeft.put(ability.getName(), usesLeft);
    }

    public void use(Ability ability, Player player) {
        PreAbilityUseEvent preUseEvent = new PreAbilityUseEvent(this, ability, player);
        Bukkit.getPluginManager().callEvent(preUseEvent);

        if(preUseEvent.isCancelled())
            return;

        this.usesLeft.merge(ability.getName(), 1, Math::subtractExact);

        Bukkit.getPluginManager().callEvent(new AbilityUseEvent(this, ability, player));
    }

    public void setRod(Rod rod) {
        this.rod = rod;
        this.rodID = rod.getName();
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
}
