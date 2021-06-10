package com.pseudonova.saverod.statics;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class InventoryUtils {
    private InventoryUtils(){}

    public static Stream<ItemStack> itemsStream(Inventory inventory) {
        return Arrays.stream(inventory.getContents())
                .filter(Objects::nonNull);
    }

}
