package com.pseudonova.saverod.events;

import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RodInstanceUsesReduceEvent extends Event  implements Cancellable {


    private final RodInstance instance;
    private final Player player;
    private final Ability ability;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled = false;


    public RodInstanceUsesReduceEvent(RodInstance instance, Player player, Ability ability) {
        this.instance = instance;
        this.player = player;
        this.ability = ability;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public RodInstance getInstance() {
        return instance;
    }

    public Ability getAbility() {
        return ability;
    }

    public Player getPlayer() {
        return player;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }
}
