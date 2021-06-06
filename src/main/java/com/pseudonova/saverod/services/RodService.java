package com.pseudonova.saverod.services;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.repositories.RodRepository;
import com.pseudonova.saverod.statics.NameSpaceCollector;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RodService implements IRodService
{
    private final RodRepository rodRepository;

    public RodService(SaveRod main , RodRepository rodRepository) {
        this.rodRepository = rodRepository;
    }

    @Override
    public boolean isRod(ItemStack item) {

        if(!item.hasItemMeta())
            return false;

        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();

        if(!dataContainer.has(NameSpaceCollector.getInstance().getRodKey(), PersistentDataType.STRING))
            return false;

        String rodName = dataContainer.get(NameSpaceCollector.getInstance().getRodKey(), PersistentDataType.STRING);

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

        String rodName = rodItem.getItemMeta().getPersistentDataContainer().get(NameSpaceCollector.getInstance().getRodKey(), PersistentDataType.STRING);

        return this.rodRepository.getValue(rodName);
    }

    @Override
    public Rod getRodByName(String name){
        return rodRepository.getValue(name);
    }



    @Override
    public void createRod(Rod rod){
        this.rodRepository.addOrUpdate(rod);
    }

}
