package com.pseudonova.saverod.services;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RodService implements IRodService
{
    private final SaveRod main;

    private final NamespacedKey ROD_KEY;

    public RodService(SaveRod main) {
        this.main = main;
        this.ROD_KEY = new NamespacedKey(main, "rod");
    }

    @Override
    public boolean isRod(ItemStack item) {

        if(!item.hasItemMeta())
            return false;

        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();

        if(!dataContainer.has(this.ROD_KEY, PersistentDataType.STRING))
            return false;

        String rodName = dataContainer.get(this.ROD_KEY, PersistentDataType.STRING);


    }

    @Override
    public Rod getRod(ItemStack rodItem) {
        return null;
    }
}
