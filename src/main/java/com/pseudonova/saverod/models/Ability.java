package com.pseudonova.saverod.models;


import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Ability {
    private final Rod rod;
    private final String name;
    private final Map<Class<? extends Event>, Consumer> supportedEvents = new HashMap<>();

    public Ability(Rod rod, String name) {
        this.rod = rod;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Rod getRod() {
        return rod;
    }

    protected <E extends Event> void when(Class<E> eventClass, Consumer<E> eventHandler){
        this.supportedEvents.put(eventClass, eventHandler);
    }

    public boolean isSupportedEvent(Event event){
        return this.supportedEvents.containsKey(event.getClass());
    }

    @SuppressWarnings("unchecked")
    public void activateWithin(Event event) {

        this.supportedEvents.get(event.getClass()).accept(event);
    }

}
