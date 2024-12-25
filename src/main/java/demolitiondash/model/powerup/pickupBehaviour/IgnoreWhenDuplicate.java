package demolitiondash.model.powerup.pickupBehaviour;

import demolitiondash.model.Player;
import demolitiondash.model.powerup.Powerup;

public class IgnoreWhenDuplicate implements PowerupPickupBehaviour {
    private static IgnoreWhenDuplicate instance;
    private IgnoreWhenDuplicate() {}
    public static IgnoreWhenDuplicate getInstance() {
        if (instance == null) {
            instance = new IgnoreWhenDuplicate();
        }
        return instance;
    }

    @Override
    public void activate(Powerup powerup, Player player) {
        if(player.getPowerups().stream().anyMatch(p -> p.getClass() == powerup.getClass())) return;
        player.addPowerup(powerup);
    }
}
