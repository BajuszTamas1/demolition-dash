package demolitiondash.model.powerup;

import demolitiondash.model.Player;
import demolitiondash.model.powerup.pickupBehaviour.IgnoreWhenDuplicate;

public class SpeedIncreasePowerup  extends Powerup{
    private final int SPEED_MULTIPLIER = 2;

    public SpeedIncreasePowerup() {
        super(IgnoreWhenDuplicate.getInstance());
    }

    /**
     * Applies the effect of increasing the player's speed.
     * Sets the speed of the player to the default speed multiplied by the speed multiplier.
     */
    @Override
    public void effect() {
        super.effect();
        player.setSpeed(Player.PLAYER_DEFAULT_SPEED * SPEED_MULTIPLIER);
    }
    @Override
    public void clear() {}
}
