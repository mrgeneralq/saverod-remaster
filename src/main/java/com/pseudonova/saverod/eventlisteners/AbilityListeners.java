package com.pseudonova.saverod.eventlisteners;

import com.pseudonova.saverod.enums.AbilityType;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.utils.InventoryUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AbilityListeners implements Listener
{
    private final IRodInstanceService rodInstanceService;

    public AbilityListeners(IRodInstanceService rodInstanceService){
        this.rodInstanceService = rodInstanceService;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        handle(event);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        handle(event);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){

        if(!event.hasItem())
            return;

        ItemStack itemStack = event.getItem();

        if(!this.rodInstanceService.isRod(itemStack))
            return;

        RodInstance rodInstance = this.rodInstanceService.getInstance(itemStack);
        Rod rod = rodInstance.getRod();

        if(!rod.hasInteractiveAbility())
            return;

        Player player = event.getPlayer();
        Ability interactiveAbility = rod.getPassiveAbilities(AbilityType.INTERACTIVE).get(0);

        if(interactiveAbility == null){
            player.sendMessage(ChatColor.RED + "This rod has no Interactive Ability!");
            return;
        }
        handle(event);
    }

    private void handle(Event event) {

       Player player = getPlayer(event);

       //if a player wasn't involved in the event, do nothing
        if(player == null)
            return;

        for(ItemStack item : InventoryUtils.getItems(player.getInventory())){

            if(!this.rodInstanceService.isRod(item))
                continue;

            RodInstance rodInstance = this.rodInstanceService.getRodInstance(item);

            rodInstance.getRod().getAbilities().stream()
                    .filter(ability -> ability.isSupportedEvent(event))
                    .forEach(ability -> rodInstance.use(ability, player));
        }
    }

    private Player getPlayer(Event event){

        if(event instanceof PlayerEvent) {
            return ((PlayerEvent) event).getPlayer();
        }
        else if(event instanceof EntityEvent) {
            EntityEvent entityEvent = (EntityEvent) event;

            if (entityEvent.getEntityType() == EntityType.PLAYER)
                return (Player) entityEvent.getEntity();
        }
        return null;
    }
}
