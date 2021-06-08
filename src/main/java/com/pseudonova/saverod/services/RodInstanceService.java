package com.pseudonova.saverod.services;

import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.repositories.RodInstanceRepository;

import java.util.Map;

import static java.util.stream.Collectors.toMap;


public class RodInstanceService implements IRodInstanceService {

    private final RodInstanceRepository rodInstanceRepository;

    public RodInstanceService(RodInstanceRepository rodInstanceRepository) {
        this.rodInstanceRepository = rodInstanceRepository;
    }

    @Override
    public RodInstance getRodInstance(String id) {
        return rodInstanceRepository.getValue(id);
    }

    @Override
    public void removeRodInstance(String id) {
        rodInstanceRepository.remove(id);
    }

    @Override
    public RodInstance getNewInstance(Rod rod) {

        RodInstance instance = null;

        do{
            //add the random ID to the constructor, so this loop creates the ID and not the Object
            //this helps with modularity + more efficient
            instance = new RodInstance(rod);
        }
        while(instanceExists(instance.getInstanceID()));


        Map<String, Integer> usesLeftMap = rod.getAbilities().stream()
                .collect(toMap(Ability::getName, Ability::getMaxUses));
        instance.setUsesLeft(usesLeftMap);

        rodInstanceRepository.addOrUpdate(instance);

        return instance;
    }

    @Override
    public boolean instanceExists(String id) {
        return rodInstanceRepository.containsKey(id);
    }
}
