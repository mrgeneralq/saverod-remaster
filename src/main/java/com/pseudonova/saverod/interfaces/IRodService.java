package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.models.Rod;
import org.bukkit.inventory.ItemStack;

public interface IRodService {

    boolean isRod(ItemStack item);
    Rod getRod(ItemStack rodItem);
}
