package com.pseudonova.saverod.services;

import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.repositories.RodInstanceRepository;

import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.toMap;


public class RodInstanceService implements IRodInstanceService {

    private final RodInstanceRepository rodInstanceRepository;

    public RodInstanceService(RodInstanceRepository rodInstanceRepository) {
        this.rodInstanceRepository = rodInstanceRepository;
    }

    @Override
    public RodInstance getRodInstance(UUID id) {
        return rodInstanceRepository.getValue(id);
    }

    @Override
    public void removeRodInstance(UUID id) {
        rodInstanceRepository.remove(id);
    }

    @Override
    public RodInstance getNewInstance(Rod rod) {

        RodInstance instance = new RodInstance(rod);

        Map<Ability, Integer> usesLeftMap = rod.getAbilities().stream()
                .collect(toMap(ability -> ability, Ability::getMaxUses));
        instance.setUsesLeft(usesLeftMap);

        
        return instance;
    }
}
