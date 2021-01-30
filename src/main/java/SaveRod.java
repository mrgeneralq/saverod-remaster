import eventlisteners.PlayerDeathEventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SaveRod extends JavaPlugin {


    @Override
    public void onEnable(){

        // this is just a quick setup
        Bukkit.getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        
    }


}
