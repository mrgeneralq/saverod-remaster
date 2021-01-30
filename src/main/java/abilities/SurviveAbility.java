package abilities;

import models.Ability;
import models.Rod;
import org.bukkit.entity.Player;

public class SurviveAbility extends HealAbility
{

    public SurviveAbility(Rod rod, double health) {
        super(rod, 20);
    }

}
