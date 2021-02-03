package com.pseudonova.saverod.services;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.repositories.RodRepository;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RodService implements IRodService
{
    private final RodRepository rodRepository;
    private final NamespacedKey rodKey;

    public RodService(SaveRod main , RodRepository rodRepository) {
        this.rodRepository = rodRepository;
        this.rodKey = new NamespacedKey(main, "rod");
    }

    @Override
    public boolean isRod(ItemStack item) {

        if(!item.hasItemMeta())
            return false;

        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();

        if(!dataContainer.has(this.rodKey, PersistentDataType.STRING))
            return false;

        String rodName = dataContainer.get(this.rodKey, PersistentDataType.STRING);

        return this.rodRepository.containsKey(rodName);

    }

    @Override
    public boolean rodExists(String name){
        return this.rodRepository.containsKey(name);
    }

    @Override
    public Rod getRod(ItemStack rodItem) {
        if(!isRod(rodItem))
            return null;

        String rodName = rodItem.getItemMeta().getPersistentDataContainer().get(this.rodKey, PersistentDataType.STRING);

        return this.rodRepository.getValue(rodName);
    }

    @Override
    public Rod getRodByName(String name){
        return rodRepository.getValue(name);
    }



    @Override
    public void createRod(String name, Rod rod){
        this.rodRepository.addOrUpdate(name, rod);
    }

}
