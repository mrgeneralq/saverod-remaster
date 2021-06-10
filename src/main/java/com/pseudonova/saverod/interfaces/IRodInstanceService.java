package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;

public interface IRodInstanceService {
    RodInstance getRodInstance(String id);
    void removeRodInstance(String id);
    RodInstance getNewInstance(Rod rod);
    boolean instanceExists(String id);
}
