package com.pseudonova.saverod.services;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRepository;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.repositories.RodRepository;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class RodService implements IRodService
{
    private final SaveRod main;
    private final RodRepository rodRepository;

    private final NamespacedKey ROD_KEY;

    public RodService(SaveRod main , RodRepository rodRepository) {
        this.main = main;
        this.rodRepository = rodRepository;
        this.ROD_KEY = new NamespacedKey(main, "rod");
    }

    @Override
    public boolean isRod(ItemStack item) {

        if(!item.hasItemMeta())
            return false;

        PersistentDataContainer dataContainer = Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer();

        if(!dataContainer.has(this.ROD_KEY, PersistentDataType.STRING))
            return false;

        String rodName = dataContainer.get(this.ROD_KEY, PersistentDataType.STRING);
        return rodRepository.containsKey(rodName);

    }

    @Override
    public boolean rodExists(String name){
        return rodRepository.containsKey(name);
    }

    @Override
    public Rod getRod(ItemStack rodItem) {
        PersistentDataContainer dataContainer = Objects.requireNonNull(rodItem.getItemMeta()).getPersistentDataContainer();
        String rodName = dataContainer.get(this.ROD_KEY, PersistentDataType.STRING);

        return rodRepository.getValue(rodName);

    }

    @Override
    public void createRod(String name, Rod rod){
        rodRepository.addOrUpdate(name, rod);
    }
}
