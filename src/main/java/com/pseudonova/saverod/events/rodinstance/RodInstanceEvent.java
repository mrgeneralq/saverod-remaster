package com.pseudonova.saverod.events.rodinstance;

import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.event.Event;

public abstract class RodInstanceEvent extends Event
{
    private final RodInstance rodInstance;

    protected RodInstanceEvent(RodInstance rodInstance) {
        this.rodInstance = rodInstance;
    }

    public RodInstance getRodInstance() {
        return rodInstance;
    }
}
