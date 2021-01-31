package com.pseudonova.saverod;

import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.eventlisteners.AbilitiesListeners;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.statics.Bootstrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class SaveRod extends JavaPlugin {

    private final Rod rod = new Rod("test");
    private Bootstrapper bootstrapper;

    @Override
    public void onEnable(){

        Rod vipRod = new Rod("vip");
        vipRod.setDisplayName("VIP ROD");
        vipRod.setMaterial(Material.BLAZE_ROD);

        this.bootstrapper = Bootstrapper.getBootstrapper();
        bootstrapper.initialize(this);

        IRodService rodService = bootstrapper.getRodService();

        if(!rodService.rodExists("vip")){
            System.out.println("The vip ROD does not exist yet!");

            rodService.createRod("vip", vipRod);
        }

      //  this.rod.addAbility(new HealAbility(this.rod, 4D));
        Bukkit.getPluginManager().registerEvents(new AbilitiesListeners(this.rod), this);


    }
}
