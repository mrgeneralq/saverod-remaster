package com.pseudonova.saverod;

import com.pseudonova.saverod.abilities.FeedAbility;
import com.pseudonova.saverod.abilities.SaveInventoryAbility;
import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.abilities.SurviveAbility;
import com.pseudonova.saverod.commands.RodCommand;
import com.pseudonova.saverod.commands.SaveRodCommand;
import com.pseudonova.saverod.eventlisteners.AbilityListeners;
import com.pseudonova.saverod.eventlisteners.rodinstance.AbilityUseListener;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;

import com.pseudonova.saverod.statics.Bootstrapper;
import com.pseudonova.saverod.statics.NamespaceKeyContainer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class SaveRod extends JavaPlugin {

    private Bootstrapper bootstrapper;
    private NamespaceKeyContainer namespaceKeyContainer;

    @Override
    public void onEnable(){

        ConfigurationSerialization.registerClass(Rod.class);
        ConfigurationSerialization.registerClass(Ability.class);

        this.namespaceKeyContainer = NamespaceKeyContainer.getContainer();
        this.namespaceKeyContainer.initialize(this);

        this.bootstrapper = Bootstrapper.getBootstrapper();
        this.bootstrapper.initialize(this);

        //register the event listeners
        registerEvents();

        getCommand("saverod").setExecutor(new SaveRodCommand(this.bootstrapper.getRodService(), bootstrapper.getRodInstanceService(), this.bootstrapper.getConfigService()));
        IRodService rodService = bootstrapper.getRodService();

        //create test rods
        Rod rod = new Rod("quinten");
        rod.addPassiveAbility(new SaveInventoryAbility(5));
        rod.addPassiveAbility(new SurviveAbility(5));
        rod.setPrimaryAbility(new HealAbility(5,5));
        rod.setSecondaryAbility(new FeedAbility(5,2));

        if(!rodService.rodExists("quinten"))
            rodService.createRod(rod);

        Bukkit.getPluginManager().registerEvents(new AbilityListeners(this.bootstrapper.getRodInstanceService()), this);

    }

    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new AbilityUseListener(), this);
    }
}
