package com.pseudonova.saverod.models;


import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Ability {
    private final Rod rod;
    private final String name;
    private final Map<Class<? extends Event>, Consumer<Event>> supportedEvents = new HashMap<>();

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
    
    @SuppressWarnings("unchecked") //safe unchecked cast
    protected <E extends Event> void when(Class<E> eventClass, Consumer<E> handler) {
        this.supportedEvents.put(eventClass, (Consumer<Event>) handler);
    }

    public boolean isSupportedEvent(Event event) {
        return this.supportedEvents.containsKey(event.getClass());
    }
    public void activateWithin(Event event) {
        this.supportedEvents.get(event.getClass()).accept(event);
    }

}
