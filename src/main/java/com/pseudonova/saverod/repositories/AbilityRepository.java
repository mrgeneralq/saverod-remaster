package com.pseudonova.saverod.repositories;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRepository;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AbilityRepository implements IRepository<String, Ability> {

    private final SaveRod main;
    private File rodFile;
    private FileConfiguration rodConfiguration;

    public AbilityRepository(SaveRod main) {
        this.main = main;
        createConfig();
    }

    @Override
    public void addOrUpdate(String abilityName, Ability ability) {

    }


    @Override
    public void addOrUpdate(String abilityName, Ability value) {

    }

    @Override
    public boolean containsKey(String abilityName) {
        return rodConfiguration.contains(getAbilityPath(abilityName));
    }

    @Override
    public Ability getValue(String key) {

        ConfigurationSection section = this.rodConfiguration.getConfigurationSection(getAbilityPath(key));

        Map<String, Object> abilityData = new HashMap<>();

        /*
         here we need some magic where we will get the correct class and put it

         we need something to map the abilities so they can be used in a command to later on retrieve a list of
         possible abilities. With the ability name specified, we also need a way to validate the input
         thinking about the above, I would say an abstract method isValid() in the ability class and override
         it in the extending class
         I quickly show // you may stop me
        */


        return null;
    }

    private void createConfig() {

        final String rodConfigName = "abilities.yml";

        rodFile = new File(this.main.getDataFolder(), rodConfigName);
        if (!rodFile.exists()) {
            rodFile.getParentFile().mkdirs();
            this.main.saveResource(rodConfigName, false);
        }

        this.rodConfiguration = new YamlConfiguration();

        try {
            rodConfiguration.load(rodFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            this.rodConfiguration.save(rodFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String getAbilityPath(String rodName){
        return String.format("abilities.%s", rodName.toLowerCase());
    }
}
