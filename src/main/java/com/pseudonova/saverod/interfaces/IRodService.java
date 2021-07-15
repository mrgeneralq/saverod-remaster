package com.pseudonova.saverod.interfaces;

import com.pseudonova.saverod.models.Rod;

public interface IRodService {

    boolean rodExists(String name);

    Rod getRodByName(String name);

    void createRod(Rod rod);
}
