package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;

import java.util.UUID;

public interface IRodInstanceService {
    RodInstance getRodInstance(UUID id);
    void removeRodInstance(UUID id);

    RodInstance getNewInstance(Rod rod);
}
