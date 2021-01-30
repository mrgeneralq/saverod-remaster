package eventlisteners;

import models.Rod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventListener implements Listener {


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e ){
        // run when playe dies

        //not actual code, just idea

        Rod rod = new Rod("my rod"); // this would have been retrieved from the service

        // problem situation, we need to initialize a new rod I believe with an itemstack, or something simular
        // maybe we an identifier in the item, Persistent data container?

        rod.activate(e);

    }


}
