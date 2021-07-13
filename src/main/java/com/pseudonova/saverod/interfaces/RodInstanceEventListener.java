package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.enums.AbilityType;
import com.pseudonova.saverod.models.RodInstance;

public interface RodInstanceEventListener {

    void onInteract(RodInstance instance, AbilityType type);

}
