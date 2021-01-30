package models;


import org.bukkit.event.Event;

import java.util.HashSet;
import java.util.Set;

public abstract class Ability {

    private final Rod rod;
    private final String name;
    private final Set<Class<? extends Event>> supportedEvents = new HashSet<>();

    public Ability(Rod rod, String name) {
        this.rod = rod;
        this.name = name;
    }
    protected void supportEvent(Class<? extends Event> eventClass){
        this.supportedEvents.add(eventClass);
    }
    public boolean isSupportedEvent(Class<? extends Event> eventClass){
        return this.supportedEvents.contains(eventClass);
    }

    public abstract void activateWithin(Event event);

    public Rod getRod() {
        return rod;
    }

    public String getName() {
        return name;
    }
}
