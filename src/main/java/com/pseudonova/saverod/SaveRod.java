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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class SaveRod extends JavaPlugin {

    private Bootstrapper bootstrapper;

    @Override
    public void onEnable(){

        this.bootstrapper = Bootstrapper.getBootstrapper();
        this.bootstrapper.initialize(this);

        IRodService rodService = bootstrapper.getRodService();

        Rod vipRod = createRod("vip", "VIP ROD", Material.BLAZE_ROD, new HealAbility(420));
        Rod testRod = createRod("test", "TEST ROD", Material.STICK, new SurviveAbility());

        if(!rodService.rodExists("vip"))
            rodService.createRod("vip", vipRod);

        if(!rodService.rodExists("test"))
            rodService.createRod("test", testRod);

        System.out.println("VIP Rod: " + rodService.getRodByName("vip").serialize());
        System.out.println("Test Rod: " + rodService.getRodByName("test").serialize());

        Bukkit.getPluginManager().registerEvents(new AbilitiesListeners(vipRod, rodService), this);

    }

    private Rod createRod(String name, String displayName, Material material, Ability... abilities){
        Rod rod = new Rod(name);
        rod.setDisplayName(displayName);
        rod.setMaterial(material);
        Arrays.stream(abilities).forEach(rod::addAbility);

        return rod;
    }
}
