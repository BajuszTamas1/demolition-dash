package demolitiondash.model.powerup.pickupBehaviour;

import demolitiondash.model.Player;
import demolitiondash.model.powerup.Powerup;

public class StackWhenDuplicate implements PowerupPickupBehaviour {
    private static StackWhenDuplicate instance;
    private StackWhenDuplicate() {}
    public static StackWhenDuplicate getInstance() {
        if (instance == null) {
            instance = new StackWhenDuplicate();
        }
        return instance;
    }
    @Override
    public void activate(Powerup powerup, Player player) {
        player.addPowerup(powerup);
    }
}
