package com.pseudonova.saverod.services;

import com.google.gson.Gson;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.persistentdatatypes.RodInstanceType;
import com.pseudonova.saverod.statics.NamespaceKeyContainer;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class RodInstanceService implements IRodInstanceService {


    //private final static RodInstanceType ROD_INSTANCE_TYPE = new RodInstanceType();
    private final IRodService rodService;
    private final static NamespacedKey ROD_INSTANCE_KEY = NamespaceKeyContainer.getContainer().getRodInstanceKey();
    private static final Gson GSON = new Gson();

    public RodInstanceService(IRodService rodService) {
        this.rodService = rodService;
    }

    @Override
    public RodInstance getRodInstance(ItemStack itemStack) {

        //TODO
        /*
        RodInstance rodInstance = this.getInstanceFromItemStack(itemStack);

        Rod rod = rodService.getRodByName(rodInstance.getRodID());

        System.out.println(rod.toString());
        rodInstance.setRod(rod);

        return rodInstance;

         */

        return null;
    }


    @Override
    public void removeRodInstance(ItemStack item) {
        removeRodInstanceFromItemStack(item);
    }

    @Override
    public RodInstance getNewInstance(Rod rod) {

        RodInstance instance = new RodInstance(rod);
        instance.setRod(rod);

        Map<String, Integer> usesLeftMap = rod.getPassiveAbilities().stream()
                .collect(toMap(Ability::getName, Ability::getMaxUses));
        instance.setUsesLeft(usesLeftMap);

        return instance;
    }

    @Override
    public void updateInstance(ItemStack itemStack) {

      //TODO
        /*
        RodInstance instance = this.getInstanceFromItemStack(itemStack);
        this.setRodInstance(itemStack, instance);

         */
    }

    @Override
    public boolean isRod(ItemStack item) {

        RodInstance rodInstance = this.getInstance(item);
        if (rodInstance == null) return false;

        return rodInstance.getRod() != null;
    }


    @Override
    public RodInstance getInstance(ItemStack item) {

        if (!item.hasItemMeta())
            return null;

        if(!item.getItemMeta().getPersistentDataContainer().has(ROD_INSTANCE_KEY, PersistentDataType.STRING))
            return null;

        String instanceJson = item.getItemMeta().getPersistentDataContainer().get(ROD_INSTANCE_KEY, PersistentDataType.STRING);
        RodInstance rodInstance = GSON.fromJson(instanceJson, RodInstance.class);
        rodInstance.setRod(this.rodService.getRodByName(rodInstance.getRodID()));

        return rodInstance;
    }

    @Override
    public ItemStack getItem(RodInstance instance) {
        ItemStack itemStack = new ItemStack(instance.getRod().getMaterial());

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(instance.getRod().getDisplayName());

        List<String> lore = instance.getLoreWithAbilities();
        meta.setLore(lore);

        itemStack.setItemMeta(meta);
        setRodInstance(itemStack, instance);

        return itemStack;
    }


    private void removeRodInstanceFromItemStack(ItemStack itemStack){
        itemStack.getItemMeta().getPersistentDataContainer().remove(ROD_INSTANCE_KEY);
    }

    private void setRodInstance(ItemStack itemStack, RodInstance instance){

        String instanceJson = GSON.toJson(instance);

        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(ROD_INSTANCE_KEY, PersistentDataType.STRING, instanceJson);
        itemStack.setItemMeta(meta);

    }


}
