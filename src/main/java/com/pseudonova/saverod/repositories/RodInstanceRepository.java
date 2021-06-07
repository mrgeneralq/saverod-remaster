package com.pseudonova.saverod.repositories;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRepository;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class RodInstanceRepository implements IRepository<String, RodInstance> {

    private final SaveRod main;
    private File rodInstanceFile;
    private FileConfiguration rodInstanceConfiguration;


    public RodInstanceRepository(SaveRod main) {
        this.main = main;
        createConfig();
    }

    @Override
    public void addOrUpdate(RodInstance rodInstance) {

        this.rodInstanceConfiguration.set(getInstancePath(rodInstance.getInstanceID()), rodInstance);
        saveConfig();

    }

    @Override
    public boolean containsKey(String instanceID) {
        return rodInstanceConfiguration.contains(getInstancePath(instanceID));
    }

    @Override
    public RodInstance getValue(String instanceID) {
        return (RodInstance) rodInstanceConfiguration.get(getInstancePath(instanceID));

    }

    @Override
    public void remove(String instanceID) {
        rodInstanceConfiguration.set(getInstancePath(instanceID), null);
    }

    private void createConfig() {

        final String rodConfigName = "instances.data";

        rodInstanceFile = new File(this.main.getDataFolder(), rodConfigName);
        if (!rodInstanceFile.exists()) {
            rodInstanceFile.getParentFile().mkdirs();
            this.main.saveResource(rodConfigName, false);
        }

        this.rodInstanceConfiguration = new YamlConfiguration();
        loadConfig();
    }

    private void loadConfig(){
        try {
            rodInstanceConfiguration.load(rodInstanceFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            this.rodInstanceConfiguration.save(rodInstanceFile);
            //WORK AROUND loadConfig();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String getInstancePath(String rodInstanceID){
        return String.format("instances.%s", rodInstanceID);
    }


}
