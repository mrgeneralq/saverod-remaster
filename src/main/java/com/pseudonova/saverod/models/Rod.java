package models;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rod {
    private final String name = "";
    private String displayName = "";
    private Material material = Material.BLAZE_ROD;
    private List<String> lore = new ArrayList<>();
    private final List<Ability> abilities = new ArrayList<>();

    public Rod(String displayName) {

        //abilities are added here


        this.displayName = displayName;
    }


    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void addAbility(Ability ability){
        this.abilities.add(ability);
    }

    public void activateWithin(Event event) {
        Class<? extends Event> eventClass = event.getClass();

        this.abilities.stream()
                .filter(ability -> ability.isSupportedEvent(eventClass))
                .forEach(ability -> ability.activateWithin(event));
    }
}