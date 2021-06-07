package com.pseudonova.saverod.eventlisteners;

import com.pseudonova.saverod.abilities.SurviveAbility;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.services.RodService;
import com.pseudonova.saverod.statics.InventoryUtils;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbilityListener implements Listener
{
    private final IRodService rodService;
    private final IRodInstanceService rodInstanceService;

    public AbilityListener(IRodService rodService, IRodInstanceService rodInstanceService){
        this.rodService = rodService;
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
    public void onPlayerInteractEvent(PlayerInteractEvent event){

        if(!event.hasItem())
            return;

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if(!rodService.isRod(itemStack)){
            player.sendMessage("this is not rod!");
            return;
        }
        RodInstance rodInstance = rodService.getInstance(itemStack);
        Rod rod = rodService.getRodByName(rodInstance.getRodID());

        SurviveAbility surviveAbility = rod.getAbility(SurviveAbility.class);

        if(surviveAbility == null){
            player.sendMessage(ChatColor.RED + "This rod doesn't have the Survive ability!");
            return;
        }
        player.sendMessage("Uses left: " + rodInstance.getUsesLeft(surviveAbility));


    }

    private void handle(Event event) {

       Player player = getPlayer(event);

       //if a player wasn't involved in the event, do nothing
        if(player == null)
            return;

        //activate the rods in the player's inventory
        for(RodInstance rodInstance : getRodsIn(player.getInventory())){
            Rod rod = rodService.getRodByName(rodInstance.getRodID());
            rod.handleEvent(event);
        }
    }

    private List<RodInstance> getRodsIn(Inventory inventory){

        return InventoryUtils.getItems(inventory).stream()
                .filter(this.rodService::isRod)
                .map(this.rodService::getInstance)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
