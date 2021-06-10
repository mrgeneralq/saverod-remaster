package com.pseudonova.saverod.eventlisteners;

import com.pseudonova.saverod.abilities.SurviveAbility;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
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

import static java.util.stream.Collectors.toList;

public class AbilityListener implements Listener
{
    private final IRodService rodService;

    public AbilityListener(IRodService rodService){
        this.rodService = rodService;
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

        ItemStack itemStack = event.getItem();

        if(!rodService.isRod(itemStack))
            return;

        RodInstance rodInstance = rodService.getInstance(itemStack);
        Rod rod = rodService.getRodByName(rodInstance.getRodID());

        SurviveAbility surviveAbility = rod.getAbility(SurviveAbility.class);
        Player player = event.getPlayer();

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
        for(Rod rod : getRodsIn(player.getInventory()))
            rod.handleEvent(event);
    }

    private List<Rod> getRodsIn(Inventory inventory){

        return InventoryUtils.itemsStream(inventory)
                .filter(this.rodService::isRod) //keep only rod items
                .map(this.rodService::getInstance) //get the RodInstance object from the item
                .map(RodInstance::getRodID) //get the Rod's name from the RodInstance
                .map(this.rodService::getRodByName)
                .collect(toList());
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
