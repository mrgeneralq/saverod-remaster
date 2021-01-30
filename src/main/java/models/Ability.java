package models;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public abstract class Ability {

    private final Rod rod;
    private final String name;

    public Ability(Rod rod, String name) {
        this.rod = rod;
        this.name = name;
    }

    public abstract void activateWithin(PlayerDeathEvent event);
    public abstract void activateWithin(EntityDamageEvent event);

    public Rod getRod() {
        return rod;
    }

    public String getName() {
        return name;
    }
}
