package models;

import org.bukkit.entity.Player;

public abstract class Ability {

    private final Rod rod;
    private final String name;

    public Ability(Rod rod, String name) {
        this.rod = rod;
        this.name = name;
    }

    public abstract void activate(Player player);

    public Rod getRod() {
        return rod;
    }

    public String getName() {
        return name;
    }
}
