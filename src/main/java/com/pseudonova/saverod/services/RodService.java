package com.pseudonova.saverod.services;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.repositories.RodRepository;
import com.pseudonova.saverod.utils.HiddenStringUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class RodService implements IRodService
{
    private final RodRepository rodRepository;
    private final IRodInstanceService rodInstanceService;

    public RodService(SaveRod main , IRodInstanceService rodInstanceService, RodRepository rodRepository) {
        this.rodRepository = rodRepository;
        this.rodInstanceService = rodInstanceService;
    }

    @Override
    public boolean isRod(ItemStack item) {
        return this.getInstance(item) != null;
    }

    @Override
    public boolean rodExists(String name){
        return this.rodRepository.containsKey(name);
    }



    @Override
    public Rod getRodByName(String name){
        return rodRepository.getValue(name);
    }



    @Override
    public void createRod(Rod rod){
        this.rodRepository.addOrUpdate(rod);
    }

    @Override
    public RodInstance getInstance(ItemStack item) {
        if(!item.hasItemMeta())
            return null;

        if(!item.getItemMeta().hasLore())
            return null;

        List<String> lore = item.getItemMeta().getLore();

        if(lore.isEmpty())
            return null;

        String instanceLine = lore.stream()
                .filter(line -> line.startsWith("saverod-instance"))
                .findFirst().orElse(null);

        if(instanceLine == null)
            return null;

        String instanceID = instanceLine.replace("saverod-instance:", "");
        System.out.println("ROD UUID IS == "  + instanceID);
        UUID convertedID = UUID.fromString(instanceID);

        RodInstance instance = rodInstanceService.getRodInstance(convertedID);
        return instance;
    }

    @Override
    public ItemStack getRodItem(RodInstance rodInstance) {

        Rod rod = this.getRodByName(rodInstance.getRodID());
        ItemStack itemStack = new ItemStack(rod.getMaterial());

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(rod.getDisplayName());

        List<String> lore = rod.getLoreWithAbilities();
        lore.add("saverod-instance:" + rodInstance.getInstanceID());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
