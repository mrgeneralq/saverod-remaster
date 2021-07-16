package com.pseudonova.saverod.events.rodinstance;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PreAbilityUseEvent extends AbilityUseEvent implements Cancellable {

    private boolean isCancelled = false;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PreAbilityUseEvent(RodInstance rodInstance, Ability ability, Player player) {
        super(rodInstance, ability, player);
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
