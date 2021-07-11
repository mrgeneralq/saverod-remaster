package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.inventory.ItemStack;

public interface IRodInstanceService {
    RodInstance getRodInstance(ItemStack itemStack);
    void removeRodInstance(ItemStack itemStack);
    RodInstance getNewInstance(Rod rod);
    void updateInstance(ItemStack itemStack);

    boolean isRod(ItemStack item);

    boolean instanceExists(ItemStack item);

    RodInstance getInstance(ItemStack item);
    ItemStack getItem(RodInstance instance);
}
