package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.inventory.ItemStack;

public interface IRodInstanceService {
    RodInstance getRodInstance(String id);
    void removeRodInstance(String id);
    RodInstance getNewInstance(Rod rod);
    void updateInstance(RodInstance instance);

    boolean isRod(ItemStack item);

    boolean instanceExists(String id);

    RodInstance getInstance(ItemStack item);
}
