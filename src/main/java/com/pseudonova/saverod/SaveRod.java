package com.pseudonova.saverod;

import com.pseudonova.saverod.abilities.abstracts.AbstractHealAbility;
import com.pseudonova.saverod.abilities.healing.HealAbility;
import com.pseudonova.saverod.abilities.healing.SurviveAbility;
import com.pseudonova.saverod.commands.RodCommand;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.statics.Bootstrapper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class SaveRod extends JavaPlugin {

    private Bootstrapper bootstrapper;

    @Override
    public void onEnable(){




        ConfigurationSerialization.registerClass(Rod.class);


        this.bootstrapper = Bootstrapper.getBootstrapper();
        this.bootstrapper.initialize(this);

        Bukkit.getPluginCommand("rod").setExecutor(new RodCommand(this.bootstrapper.getRodService()));



        IRodService rodService = bootstrapper.getRodService();
        Rod rod = new Rod("quinten");
        rod.addAbility(new HealAbility(5.0));
        Rod koen = new Rod("koen");
        koen.addAbility(new SurviveAbility());


        if(!rodService.rodExists("quinten"))
            rodService.createRod(rod);

        if(!rodService.rodExists("koen"))
            rodService.createRod(koen);

        System.out.println(rodService.getRodByName("quinten").serialize());
        System.out.println(rodService.getRodByName("koen").serialize());
    }
}
