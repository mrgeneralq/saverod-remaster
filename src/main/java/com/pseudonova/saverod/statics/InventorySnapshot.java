package com.pseudonova.saverod.statics;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventorySnapshot
{
    private final Map<Integer, ItemStack> itemsInSlots = new HashMap<>();

    public InventorySnapshot(Inventory inv){
        cloneContentOf(inv);
    }

    public ItemStack getItemAt(int slot){
        return this.itemsInSlots.get(slot);
    }

    public void setItemAt(int slot, ItemStack item){
        this.itemsInSlots.put(slot, item.clone());
    }

    public void equip(Inventory inv){
        this.itemsInSlots.forEach(inv::setItem);
    }

    private void cloneContentOf(Inventory inv){

        for(int i = 0; i < inv.getSize(); i++){
            ItemStack item = inv.getItem(i);

            if(item != null)
                this.itemsInSlots.put(i, item.clone());
        }

    }
}
