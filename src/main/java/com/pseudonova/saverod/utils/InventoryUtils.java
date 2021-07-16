package com.pseudonova.saverod.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InventoryUtils {
    private InventoryUtils(){}

    public static Stream<ItemStack> itemsStream(Inventory inventory) {
        return Arrays.stream(inventory.getContents())
                .filter(Objects::nonNull);
    }

    public static List<ItemStack> getItems(Inventory inventory){
        return itemsStream(inventory).collect(Collectors.toList());
    }

}
