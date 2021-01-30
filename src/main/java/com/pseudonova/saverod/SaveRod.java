package com.pseudonova.saverod;

import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.eventlisteners.AbilitiesListeners;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SaveRod extends JavaPlugin {

    private final Rod rod = new Rod("rod display name", false);

    @Override
    public void onEnable(){

        this.rod.addAbility(new HealAbility(this.rod, 4D));

        Bukkit.getPluginManager().registerEvents(new AbilitiesListeners(this.rod), this);
    }
}
