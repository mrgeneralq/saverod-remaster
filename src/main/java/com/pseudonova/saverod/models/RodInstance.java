package com.pseudonova.saverod.models;
import com.pseudonova.saverod.events.RodInstanceUsesReduceEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;


public class RodInstance {

    private final String instanceID;

    private transient Rod rod;
    private String rodID;

    private Map<String, Integer> usesLeft;

    public RodInstance(Rod rod) {

        this.instanceID = createRandomInstanceID();
        this.rodID = rod.getName();
        this.rod = rod;
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

    public void setUsesLeft(Ability ability, int usesLeft){
        this.usesLeft.put(ability.getName(), usesLeft);
    }

    public void setUsesLeft(Map<String, Integer> usesLeft) {
        this.usesLeft = usesLeft;
    }

    public void reduceUsesleft(Player player, Ability ability){

        RodInstanceUsesReduceEvent event = new RodInstanceUsesReduceEvent(this, ability);

        if(event.isCancelled())
            return;

        //TODO move to Event Listener
        String abilityName = ability.getName();

        if(this.usesLeft.get(abilityName) == 0)
            return;

        this.usesLeft.merge(abilityName, 1, Math::subtractExact);
    }


    private static String createRandomInstanceID(){
        return UUID.randomUUID().toString().substring(0, 7).replace("-", "");
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


    public Rod getRod() {
        return rod;
    }

}
