package com.pseudonova.saverod;

import com.pseudonova.saverod.abilities.FeedAbility;
import com.pseudonova.saverod.abilities.SaveInventoryAbility;
import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.abilities.SurviveAbility;
import com.pseudonova.saverod.commands.RodCommand;
import com.pseudonova.saverod.eventlisteners.AbilityListener;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.statics.Bootstrapper;
import com.pseudonova.saverod.statics.NameSpaceCollector;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class SaveRod extends JavaPlugin {

    private Bootstrapper bootstrapper;

    @Override
    public void onEnable(){

        ConfigurationSerialization.registerClass(Rod.class);
        ConfigurationSerialization.registerClass(RodInstance.class);

        this.bootstrapper = Bootstrapper.getBootstrapper();
        this.bootstrapper.initialize(this);

        getCommand("rod").setExecutor(new RodCommand(this.bootstrapper.getRodService(), bootstrapper.getRodInstanceService()));

        IRodService rodService = bootstrapper.getRodService();

        //create test rods
        Rod rod = new Rod("quinten");
        rod.addAbility(new HealAbility(5.0, 5));
        rod.addAbility(new SaveInventoryAbility(5));
        rod.addAbility(new SurviveAbility(4));

        Rod koen = new Rod("koen");
        koen.addAbility(new SurviveAbility(6));


        if(!rodService.rodExists("quinten"))
            rodService.createRod(rod);

        if(!rodService.rodExists("koen"))
            rodService.createRod(koen);

        Bukkit.getPluginManager().registerEvents(new AbilityListener(this.bootstrapper.getRodService(), bootstrapper.getRodInstanceService()), this);
        run();
    }

    void run(){

       //test codes

    }
}
