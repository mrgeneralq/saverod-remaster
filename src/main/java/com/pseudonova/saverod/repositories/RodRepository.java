package com.pseudonova.saverod.repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRepository;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
    public void addOrUpdate(Rod rod) {


        this.rodConfiguration.set(getRodPath(rod.getName()), rod);
        saveConfig();

    }

    @Override
    public boolean containsKey(String rodName) {
        return rodConfiguration.contains(getRodPath(rodName));
    }

    @Override
    public Rod getValue(String rodName) {
        return (Rod) rodConfiguration.get(getRodPath(rodName));

    }

    @Override
    public void remove(String rodName) {
        rodConfiguration.set(getRodPath(rodName), null);
    }

    private void createConfig() {

        final String rodConfigName = "rods.yml";

        rodFile = new File(this.main.getDataFolder(), rodConfigName);
        if (!rodFile.exists()) {
            rodFile.getParentFile().mkdirs();
            this.main.saveResource(rodConfigName, false);
        }

        this.rodConfiguration = new YamlConfiguration();
        loadConfig();
    }

    private void loadConfig(){
        try {
            rodConfiguration.load(rodFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            this.rodConfiguration.save(rodFile);
           //WORK AROUND loadConfig();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String getRodPath(String rodName){
        return String.format("rods.%s", rodName.toLowerCase());
    }

}
