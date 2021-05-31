package com.pseudonova.saverod.repositories;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRepository;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class RodRepository implements IRepository<String, Rod> {

    private final SaveRod main;
    private File rodFile;
    private FileConfiguration rodConfiguration;

    public RodRepository(SaveRod main) {
        this.main = main;
        createConfig();

        loadRod();
    }

    @Override
    public void addOrUpdate(String rodName, Rod rod) {

        Map<String, Object> serializedObject = rod.serialize();

        this.rodConfiguration.set(getRodPath(rodName), serializedObject);
        saveConfig();

    }

    @Override
    public boolean containsKey(String rodName) {
        return rodConfiguration.contains(getRodPath(rodName));
    }

    @Override
    public Rod getValue(String key) {

        ConfigurationSection section = this.rodConfiguration.getConfigurationSection(getRodPath(key));

        Map<String, Object> rodData = new HashMap<>();
        rodData.put("name", section.get("name"));
        rodData.put("display-name", section.get("display-name"));
        rodData.put("material", section.get("material"));
        rodData.put("must-be-held", section.get("must-be-held"));

        return new Rod(rodData);
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

    private void loadRod(){
        ConfigurationSection section = this.rodConfiguration.getConfigurationSection(getRodPath("vip"));
        Map<String, Object> map = section.getKeys(false).stream().collect(toMap(Function.identity(), section::get));
        System.out.println(map);
    }

    private String getRodPath(String rodName){
        return String.format("rods.%s", rodName.toLowerCase());
    }
}
