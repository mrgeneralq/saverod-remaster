package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;

import org.bukkit.inventory.ItemStack;

public interface IRodService {

    boolean rodExists(String name);

    Rod getRodByName(String name);

    void createRod(Rod rod);

}
