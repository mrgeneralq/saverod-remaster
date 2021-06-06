package com.pseudonova.saverod.repositories;

import com.pseudonova.saverod.interfaces.IRepository;
import com.pseudonova.saverod.models.RodInstance;

import java.util.UUID;

public class RodInstanceRepository implements IRepository<UUID, RodInstance> {


    @Override
    public void addOrUpdate(RodInstance instance) {

    }

    @Override
    public boolean containsKey(UUID instanceID) {
        return true;
    }

    @Override
    public RodInstance getValue(UUID instanceID) {
        return null;
    }

    @Override
    public void remove(UUID key) {

    }
}
