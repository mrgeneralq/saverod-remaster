package com.pseudonova.saverod.models;

import com.pseudonova.saverod.enums.AbilityType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Ability implements ConfigurationSerializable {
    private final String name;
    private final int maxUses;
    private final AbilityType type;
    private final Map<Class<? extends Event>, Consumer<? extends Event>> supportedEvents = new HashMap<>();

    public Ability(String name, AbilityType type, int maxUses) {
        this.name = name;
        this.type = type;
        this.maxUses = maxUses;

    }

    public String getName() {
        return name;
    }

    public AbilityType getType(){
        return this.type;
    }

    @SuppressWarnings("unchecked") //safe unchecked cast
    public <E extends Event> void handleEvent(E event) {
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


    public int getMaxUses(){
        return this.maxUses;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("max-uses", this.maxUses);
        return map;
    }
}
