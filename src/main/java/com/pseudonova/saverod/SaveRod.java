package com.pseudonova.saverod;

import com.pseudonova.saverod.abilities.SaveInventoryAbility;
import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.abilities.SurviveAbility;
import com.pseudonova.saverod.commands.RodCommand;
import com.pseudonova.saverod.eventlisteners.AbilitiesListeners;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.statics.Bootstrapper;
import com.pseudonova.saverod.statics.NameSpaceCollector;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class SaveRod extends JavaPlugin {

    private Bootstrapper bootstrapper;
    private NameSpaceCollector nameSpaceCollector;

    @Override
    public void onEnable(){

        ConfigurationSerialization.registerClass(Rod.class);

        this.bootstrapper = Bootstrapper.getBootstrapper();
        this.bootstrapper.initialize(this);

        this.nameSpaceCollector = NameSpaceCollector.getInstance();
        this.nameSpaceCollector.initialize(this);

        Bukkit.getPluginCommand("rod").setExecutor(new RodCommand(this.bootstrapper.getRodService()));


        IRodService rodService = bootstrapper.getRodService();

        //create test rods
        Rod rod = new Rod("quinten");
        rod.addAbility(new HealAbility(5.0));
        rod.addAbility(new SaveInventoryAbility());

        Rod koen = new Rod("koen");
        koen.addAbility(new SurviveAbility());


        if(!rodService.rodExists("quinten"))
            rodService.createRod(rod);

        if(!rodService.rodExists("koen"))
            rodService.createRod(koen);

        System.out.println(rodService.getRodByName("quinten").serialize());
        System.out.println(rodService.getRodByName("koen").serialize());

        Bukkit.getPluginManager().registerEvents(new AbilitiesListeners(this.bootstrapper.getRodService()), this);
    }
}
