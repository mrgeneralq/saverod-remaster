package abilities;

import models.Ability;
import models.Rod;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandsAbility extends Ability
{
    private final Set<String> commands = new HashSet<>();

    public CommandsAbility(Rod rod) {
        super(rod, "commands");
    }

    public void addCommand(String command) {
        this.commands.add(command);
    }

    public void removeCommand(String command){
        this.commands.remove(command);
    }

    public Set<String> getCommands(){
        return new HashSet<>(this.commands);
    }

    @Override
    public void activate(Player player) {

        for(String command : commands) {
            String finalCommand = replacePlaceholders(command, player);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
        }
    }

    private String replacePlaceholders(String command, Player player){
        return command.replace("%player%", player.getName());
    }
}
