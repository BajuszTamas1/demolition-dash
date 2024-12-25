package demolitiondash.model.powerup.pickupBehaviour;

import demolitiondash.model.Player;
import demolitiondash.model.powerup.Powerup;

public interface PowerupPickupBehaviour {
    //ADD, REPLACE, IGNORE
    void activate(Powerup powerup, Player player);
}
