package com.pseudonova.saverod.events;
import com.pseudonova.saverod.interfaces.SaveRodEvent;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.entity.Player;

public class RodInstanceInteractEvent implements SaveRodEvent {

    private final RodInstance rodInstance;
    private final Player player;
    //TODO private final InteractionType (primary|secondary) <-----

    public RodInstanceInteractEvent(RodInstance rodInstance, Player player) {
        this.rodInstance = rodInstance;
        this.player = player;
    }

    public RodInstance getRodInstance() {
        return rodInstance;
    }

    public Player getPlayer() {
        return player;
    }
}
