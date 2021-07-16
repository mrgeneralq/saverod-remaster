package com.pseudonova.saverod.eventlisteners.rodinstance;

import com.pseudonova.saverod.events.rodinstance.AbilityUseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AbilityUseListener implements Listener {

    @EventHandler
    public void updateUsesLeft(AbilityUseEvent event){

        ItemStack rodItem = event.getRodInstance().getRodItem();

        ItemMeta meta = rodItem.getItemMeta();
        meta.setLore(event.getRodInstance().getLoreWithAbilities());
        rodItem.setItemMeta(meta);
    }
}
