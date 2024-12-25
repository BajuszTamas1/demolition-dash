package demolitiondash.model.powerup;

import demolitiondash.model.powerup.pickupBehaviour.ReplaceWhenDuplicate;

import java.time.Instant;

public class GhostPowerup extends Powerup {

    public static final int DURATION = 10_000;

    public GhostPowerup() {
        super(ReplaceWhenDuplicate.getInstance(), DURATION);
    }

    /**
     * Applies the effect of the ghost powerup.
     * Sets the player as a ghost.
     */
    @Override
    public void effect() {
        super.effect();
        player.setGhost(true);
    }

    /**
     * Clears any effects of the ghost powerup.
     * Resets the player's ghost state and forces the player to die if colliding with static elements.
     */
    @Override
    public void clear() {
        player.setGhost(false);
        if(player.isCollidingWithAny(player.getMap().getStaticElems())) player.forceDie();
    }

    public boolean isExpiring(){
        return Instant.now().isAfter(endTime.minusSeconds(3));
    }
}
