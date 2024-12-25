package demolitiondash.model.powerup;

import demolitiondash.model.powerup.pickupBehaviour.ReplaceWhenDuplicate;
import java.time.Instant;

public class InvincibilityPowerup extends Powerup{
    public static final int DURATION = 10_000;

    public InvincibilityPowerup(){
        super(ReplaceWhenDuplicate.getInstance(), DURATION);
    }

    /**
     * Applies the effect of the invincibility powerup.
     * Sets the player as invincible.
     */
    @Override
    public void effect() {
        super.effect();
        player.setInvincible(true);
    }

    @Override
    public void clear() {
        player.setInvincible(false);
    }

    @Override
    public boolean isExpiring(){
        return Instant.now().isAfter(endTime.minusSeconds(3));
    }
}
