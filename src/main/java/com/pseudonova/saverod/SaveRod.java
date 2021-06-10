package com.pseudonova.saverod;

import com.pseudonova.saverod.abilities.FeedAbility;
import com.pseudonova.saverod.abilities.SaveInventoryAbility;
import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.abilities.SurviveAbility;
import com.pseudonova.saverod.commands.RodCommand;
import com.pseudonova.saverod.eventlisteners.AbilityListener;
import com.pseudonova.saverod.factories.StringAbilityFactory;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.statics.Bootstrapper;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;


public class SaveRod extends JavaPlugin {

    private Bootstrapper bootstrapper;

    @Override
    public void onEnable(){

        ConfigurationSerialization.registerClass(Rod.class);
        ConfigurationSerialization.registerClass(RodInstance.class);
        ConfigurationSerialization.registerClass(Ability.class);

        this.bootstrapper = Bootstrapper.getBootstrapper();
        this.bootstrapper.initialize(this);

        getCommand("rod").setExecutor(new RodCommand(this.bootstrapper.getRodService(), bootstrapper.getRodInstanceService()));

        IRodService rodService = bootstrapper.getRodService();

        //create test rods
        Rod rod = new Rod("quinten");
        rod.addAbility(new SaveInventoryAbility(5));

        if(!rodService.rodExists("quinten"))
            rodService.createRod(rod);

        Bukkit.getPluginManager().registerEvents(new AbilityListener(this.bootstrapper.getRodService()), this);
        run();
    }

    void run(){

       //test codes

    }
}
