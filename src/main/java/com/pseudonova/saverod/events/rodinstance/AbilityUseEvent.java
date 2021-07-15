package com.pseudonova.saverod.events.rodinstance;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class AbilityUseEvent extends RodInstanceEvent
{
    private final Player player;
    private final Ability ability;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public AbilityUseEvent(RodInstance rodInstance, Ability ability, Player player) {
        super(rodInstance);

        this.ability = ability;
        this.player = player;
    }

    public Ability getAbility() {
        return ability;
    }

    public Player getPlayer(){
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
