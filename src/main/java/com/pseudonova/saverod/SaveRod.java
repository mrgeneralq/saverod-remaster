package com.pseudonova.saverod;

import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.abilities.SaveInventoryAbility;
import com.pseudonova.saverod.abilities.SurviveAbility;
import com.pseudonova.saverod.eventlisteners.AbilitiesListeners;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.statics.Bootstrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveRod extends JavaPlugin {

    private Bootstrapper bootstrapper;

    @Override
    public void onEnable(){

        ConfigurationSerialization.registerClass(Rod.class);

        this.bootstrapper = Bootstrapper.getBootstrapper();
        this.bootstrapper.initialize(this);

        IRodService rodService = bootstrapper.getRodService();
        Rod rod = new Rod("quinten");
        Rod koen = new Rod("koen");


        if(!rodService.rodExists("quinten"))
            rodService.createRod(rod);

        if(!rodService.rodExists("koen"))
            rodService.createRod(koen);

        System.out.println(rodService.getRodByName("quinten").serialize());
        System.out.println(rodService.getRodByName("koen").serialize());
    }
}
