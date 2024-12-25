package demolitiondash.model.powerup.pickupBehaviour;

import demolitiondash.model.Player;
import demolitiondash.model.powerup.Powerup;

public class ReplaceWhenDuplicate implements PowerupPickupBehaviour {
    private static ReplaceWhenDuplicate instance;
    private ReplaceWhenDuplicate() {}
    public static ReplaceWhenDuplicate getInstance() {
        if (instance == null) {
            instance = new ReplaceWhenDuplicate();
        }
        return instance;
    }
    @Override
    public void activate(Powerup powerup, Player player) {
        player.getPowerups().removeIf(p -> p.getClass() == powerup.getClass());
        player.addPowerup(powerup);
    }
}
