package com.pseudonova.saverod.services;

import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.repositories.RodInstanceRepository;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class RodInstanceService implements IRodInstanceService {

    private final RodInstanceRepository rodInstanceRepository;
    private final IRodService rodService;

    public RodInstanceService(IRodService rodService, RodInstanceRepository rodInstanceRepository) {
        this.rodService = rodService;
        this.rodInstanceRepository = rodInstanceRepository;
    }

    @Override
    public RodInstance getRodInstance(String id) {

        RodInstance rodInstance = rodInstanceRepository.getValue(id);

        Rod rod = rodService.getRodByName(rodInstance.getRodID());

        System.out.println(rod.toString());

        rodInstance.setRod(rod);

        return rodInstance;
    }

    @Override
    public void removeRodInstance(String id) {
        rodInstanceRepository.remove(id);
    }

    @Override
    public RodInstance getNewInstance(Rod rod) {

        RodInstance instance = null;

        do {
            //add the random ID to the constructor, so this loop creates the ID and not the Object
            //this helps with modularity + more efficient
            instance = new RodInstance(rod);
        }
        while (instanceExists(instance.getInstanceID()));

        instance.setRod(rod);

        Map<String, Integer> usesLeftMap = rod.getPassiveAbilities().stream()
                .collect(toMap(Ability::getName, Ability::getMaxUses));
        instance.setUsesLeft(usesLeftMap);

        rodInstanceRepository.addOrUpdate(instance);

        return instance;
    }

    @Override
    public void updateInstance(RodInstance instance) {
        this.rodInstanceRepository.addOrUpdate(instance);
    }

    @Override
    public boolean isRod(ItemStack item) {

        RodInstance rodInstance = this.getInstance(item);
        if (rodInstance == null) return false;

        return rodInstance.getRod() != null;
    }

    @Override
    public boolean instanceExists(String id) {
        return rodInstanceRepository.containsKey(id);
    }


    @Override
    public RodInstance getInstance(ItemStack item) {

        if (!item.hasItemMeta())
            return null;


        if (!item.getItemMeta().hasLore())
            return null;

        List<String> lore = item.getItemMeta().getLore();

        if (lore.isEmpty())
            return null;

        String instanceLine = lore.stream()
                .map(ChatColor::stripColor)
                .filter(line -> line.startsWith(RodInstance.ROD_IDENTIFIER))
                .findFirst()
                .orElse(null);

        if (instanceLine == null)
            return null;

        String instanceID = instanceLine.replace(RodInstance.ROD_IDENTIFIER, "");


        RodInstance rodInstance = rodInstanceRepository.getValue(instanceID);
        Rod rod = rodService.getRodByName(rodInstance.getRodID());
        rodInstance.setRod(rod);

        return rodInstance;

    }
}
