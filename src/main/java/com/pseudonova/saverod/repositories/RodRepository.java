package com.pseudonova.saverod.repositories;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRepository;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RodRepository implements IRepository<String, Rod> {

    private final SaveRod main;
    private File rodFile;
    private FileConfiguration rodConfiguration;

    public RodRepository(SaveRod main) {
        this.main = main;
        createConfig();
    }

    @Override
    public void addOrUpdate(String rodName, Rod rod) {

        Map<String, Object> serializedObject = rod.serialize();

        getConfig().set(getRodPath(rodName), serializedObject);
        saveConfig();

    }

    @Override
    public boolean containsKey(String rodName) {
        return getConfig().contains(getRodPath(rodName));
    }

    @Override
    public Rod getValue(String key) {


        return null;
    }

    private void createConfig() {

        final String rodConfigName = "rods.yml";

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

    private FileConfiguration getConfig() {
        return this.rodConfiguration;
    }

    private String getRodPath(String rodName){
        return String.format("rods.%s", rodName.toLowerCase());
    }
}
