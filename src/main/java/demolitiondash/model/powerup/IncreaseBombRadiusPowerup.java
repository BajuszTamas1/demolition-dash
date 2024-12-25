package demolitiondash.model.powerup;

import demolitiondash.model.powerup.pickupBehaviour.StackWhenDuplicate;

public class IncreaseBombRadiusPowerup extends Powerup{
    public IncreaseBombRadiusPowerup() {
        super(StackWhenDuplicate.getInstance());
    }

    /**
     * Applies the effect of increasing the bomb radius.
     * Increases the explosion radius of the player by 1.
     */
    @Override
    public void effect() {
        super.effect();
        int r = player.getExplosionRadius() + 1;
        player.setExplosionRadius(r);
    }

    @Override
    public void clear() {}
}
