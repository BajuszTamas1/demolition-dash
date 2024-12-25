package demolitiondash.model.powerup;

import demolitiondash.model.powerup.pickupBehaviour.StackWhenDuplicate;

public class BarricadePowerup extends Powerup
{
    public BarricadePowerup() {
        super(StackWhenDuplicate.getInstance());
    }

    /**
     * Applies the effect of the barricade powerup.
     * Increases the maximum barricade count of the player by 3.
     */
    @Override
    public void effect() {
        super.effect();
        var curr = player.getMaxBarrierCount();
        player.setMaxBarrierCount(curr + 3);
    }

    @Override
    public void clear() {}
}
