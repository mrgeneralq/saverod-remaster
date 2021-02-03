package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.models.Rod;
import org.bukkit.inventory.ItemStack;

public interface IRodService {

    boolean isRod(ItemStack item);

    boolean rodExists(String name);

    Rod getRod(ItemStack rodItem);

    Rod getRodByName(String name);

    void createRod(String name, Rod rod);
}
