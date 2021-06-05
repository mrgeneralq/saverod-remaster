package com.pseudonova.saverod.models;

import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Ability {
    private final String name;
    private final Map<Class<? extends Event>, Consumer<? extends Event>> supportedEvents = new HashMap<>();

    public Ability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked") //safe unchecked cast
    public <E extends Event> void activateWithin(E event) {
        Consumer<E> handler = (Consumer<E>) this.supportedEvents.get(event.getClass());
        handler.accept(event);
    }

    public <E extends Event> void when(Class<E> eventClass, Consumer<E> handler) {
        this.supportedEvents.put(eventClass, handler);
    }

    public boolean isSupportedEvent(Event event) {
        return this.supportedEvents.containsKey(event.getClass());
    }

    public void unsupportEvent(Class<? extends Event> eventClass) {
        this.supportedEvents.remove(eventClass);
    }

    public abstract String serializeToConfig();

}
