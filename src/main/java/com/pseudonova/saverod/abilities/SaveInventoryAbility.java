package abilities;

import models.Ability;
import models.Rod;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SaveInventoryAbility extends Ability {

    public SaveInventoryAbility(Rod rod) {
        super(rod, "save-inventory");

        supportEvent(PlayerDeathEvent.class);
        supportEvent(PlayerRespawnEvent.class);
    }

    @Override
    public void activateWithin(Event event)
    {
        Player player = ((PlayerEvent) event).getPlayer();

        if(event instanceof PlayerDeathEvent)
            storeInventory(player);

        else if(event instanceof PlayerRespawnEvent)
            setSavedInventory(player);
    }

    //these we'll implement later, first of all the basic design of the project
    private void storeInventory(Player player) {

    }
    private void setSavedInventory(Player player) {

    }
}
