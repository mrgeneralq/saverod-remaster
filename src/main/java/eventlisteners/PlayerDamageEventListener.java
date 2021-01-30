package eventlisteners;

import models.Rod;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDamageEventListener implements Listener {


    @EventHandler
    public void onPlayerDeath(EntityDamageEvent e ){


        if(e.getEntityType() != EntityType.PLAYER)
            return;


        Rod rod = new Rod("my rod"); // this would have been retrieved from the service
        rod.activate(e);

    }


}
