package com.pseudonova.saverod.repositories;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.abilities.HealAbility;
import com.pseudonova.saverod.interfaces.IRepository;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

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

    private Map<String, Object> readRodMap(String rodName) {

        System.out.println("rodname: " + rodName);

        Map<String, Object> rodMap = new HashMap<>();
        rodMap.put("name", (String) this.getRodProperty(rodName, "name"));
        rodMap.put("display-name", (String) this.getRodProperty(rodName, "display-name"));
        rodMap.put("must-be-held", (Boolean) this.getRodProperty(rodName, "must-be-held"));

        System.out.println("Display name: " + this.getRodProperty(rodName, "display-name"));
        System.out.println("Material name: " + this.rodConfiguration.get(String.format("rods.%s.material", rodName)));
        rodMap.put("material", Material.matchMaterial((String) this.getRodProperty(rodName, "material")));


        List<String> abilities = (List<String>) this.rodConfiguration.get(getRodPath(rodName) + ".abilities");

        try{
            Ability ability = parseAbility(abilities.get(0));
            System.out.println(ability.serializeToConfig());
        }catch (Exception ex){

        }

        return rodMap;
    }

    private Object getRodProperty(String rodName, String propertyName){

        String rodPath = this.getRodPath(rodName);
        return this.rodConfiguration.get(String.format("%s.%s", rodPath, propertyName));
    }



    private String getRodPath(String rodName){
        return String.format("rods.%s", rodName.toLowerCase());
    }

    private List<Ability> parseAbilities(List<String> abilitiesStrings){
        return abilitiesStrings.stream()
                .map(abilityString -> {
                    try {
                        return parseAbility(abilityString);
                    }
                    catch(Exception e) {
                        throw new RuntimeException(String.format("Couldn't format the ability '%s'!", abilityString));
                    }
                })
                .collect(Collectors.toList());
    }

    private static Ability parseAbility(String configLine) throws ReflectiveOperationException {
        String[] data = configLine.split(" ");
        String abilityName = data[0];
        String[] parameters = Arrays.copyOfRange(data, 1, data.length);

        Class<?> abilityClass = Class.forName(String.format("com.pseudonova.saverod.abilities.%sAbility", WordUtils.capitalizeFully(abilityName)));
        Method deserializerStaticMethod = abilityClass.getDeclaredMethod("deserialize", String[].class);

        return (Ability) deserializerStaticMethod.invoke(null, new Object[]{parameters});
    }
}
