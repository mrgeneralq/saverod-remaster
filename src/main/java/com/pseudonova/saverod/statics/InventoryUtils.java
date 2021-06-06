package com.pseudonova.saverod.statics;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InventoryUtils {
    private InventoryUtils(){}

    public static List<ItemStack> getItems(Inventory inventory){
        return Arrays.stream(inventory.getContents())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
